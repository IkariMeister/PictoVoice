package com.pictovoice.server

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {
    @Test
    fun health_endpoint_returns_ok() =
        testApplication {
            val response = client.get("/health")
            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals("""{"status":"ok"}""", response.bodyAsText())
        }

    @Test
    fun vocabulary_manifest_endpoint_returns_manifest_payload() =
        testApplication {
            val response = client.get("/v1/vocabulary/manifest")
            val body = response.bodyAsText()

            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(body.contains("\"revision\":\"revision-1\""))
            assertTrue(body.contains("\"generatedAt\":\"2026-01-01T00:00:00Z\""))
        }

    @Test
    fun vocabulary_delta_endpoint_returns_query_based_revision() =
        testApplication {
            val response = client.get("/v1/vocabulary/delta?since=revision-0")
            val body = response.bodyAsText()

            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(body.contains("\"revision\":\"revision-1\""))
            assertTrue(body.contains("\"spokenText\":\"Yes\""))
            assertTrue(body.contains("\"deletes\":[]"))
        }

    @Test
    fun vocabulary_delta_without_since_returns_bad_request() =
        testApplication {
            val response = client.get("/v1/vocabulary/delta")
            val body = response.bodyAsText()

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertTrue(body.contains("since"))
        }

    @Test
    fun device_register_with_valid_payload_returns_no_content() =
        testApplication {
            val response =
                client.post("/v1/devices/register") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        """
                        {
                          "platform":"android",
                          "token":"fcm-token-123",
                          "deviceId":"device-42"
                        }
                        """.trimIndent(),
                    )
                }

            assertEquals(HttpStatusCode.NoContent, response.status)
            assertEquals("", response.bodyAsText())
        }

    @Test
    fun device_register_without_required_fields_returns_bad_request() =
        testApplication {
            val response =
                client.post("/v1/devices/register") {
                    contentType(ContentType.Application.Json)
                    setBody("""{"platform":"android"}""")
                }

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertTrue(response.bodyAsText().contains("Invalid registration payload"))
        }

    @Test
    fun device_register_with_invalid_platform_returns_bad_request() =
        testApplication {
            val response =
                client.post("/v1/devices/register") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        """
                        {
                          "platform":"web",
                          "token":"token"
                        }
                        """.trimIndent(),
                    )
                }

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertTrue(response.bodyAsText().contains("Invalid registration payload"))
        }

    @Test
    fun caregiver_layout_without_bearer_token_returns_unauthorized() =
        testApplication {
            val response = client.put("/v1/caregiver/layout") {
                contentType(ContentType.Application.Json)
                setBody("""{"id":"layout-1"}""")
            }

            assertEquals(HttpStatusCode.Unauthorized, response.status)
            assertTrue(response.bodyAsText().contains("Missing or invalid bearer token"))
        }

    @Test
    fun caregiver_layout_with_invalid_bearer_token_returns_unauthorized() =
        testApplication {
            val response =
                client.put("/v1/caregiver/layout") {
                    header("Authorization", "Bearer invalid-token")
                    contentType(ContentType.Application.Json)
                    setBody("""{"id":"layout-1"}""")
                }

            assertEquals(HttpStatusCode.Unauthorized, response.status)
            assertTrue(response.bodyAsText().contains("Unauthorized caregiver token"))
        }

    @Test
    fun caregiver_layout_with_valid_bearer_token_returns_ok() =
        testApplication {
            val response =
                client.put("/v1/caregiver/layout") {
                    header("Authorization", "Bearer caregiver-dev-token")
                    contentType(ContentType.Application.Json)
                    setBody(
                        """
                        {
                          "id":"layout-1",
                          "rows":2,
                          "columns":2,
                          "cells":["yes","no",null,null],
                          "version":"v1"
                        }
                        """.trimIndent(),
                    )
                }

            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("\"revision\":\"revision-2\""))
        }
}
