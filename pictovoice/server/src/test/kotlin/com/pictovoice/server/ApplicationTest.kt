package com.pictovoice.server

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
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
            application {
                module()
            }

            val response = client.get("/health")
            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals("""{"status":"ok"}""", response.bodyAsText())
        }

    @Test
    fun vocabulary_manifest_endpoint_returns_manifest_payload() =
        testApplication {
            application {
                module()
            }

            val response = client.get("/v1/vocabulary/manifest")
            val body = response.bodyAsText()

            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(body.contains("\"revision\":\"revision-1\""))
            assertTrue(body.contains("\"generatedAt\":\"2026-01-01T00:00:00Z\""))
        }

    @Test
    fun vocabulary_delta_endpoint_returns_query_based_revision() =
        testApplication {
            application {
                module()
            }

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
            application {
                module()
            }

            val response = client.get("/v1/vocabulary/delta")
            val body = response.bodyAsText()

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertTrue(body.contains("since"))
        }

    @Test
    fun device_register_with_valid_payload_returns_no_content() =
        testApplication {
            application {
                module()
            }

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
            application {
                module()
            }

            val response =
                client.post("/v1/devices/register") {
                    contentType(ContentType.Application.Json)
                    setBody("""{"platform":"android"}""")
                }

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertTrue(response.bodyAsText().contains("Invalid JSON body"))
        }

    @Test
    fun device_register_with_invalid_platform_returns_bad_request() =
        testApplication {
            application {
                module()
            }

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
}
