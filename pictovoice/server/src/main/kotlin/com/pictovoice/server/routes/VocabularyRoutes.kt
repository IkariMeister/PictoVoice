package com.pictovoice.server.routes

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

private const val ManifestEtag = "\"revision-1\""
private const val ManifestRevision = "revision-1"
private const val ManifestGeneratedAt = "2026-01-01T00:00:00Z"

fun Route.vocabularyRoutes() {
    route("/v1/vocabulary") {
        get("/manifest") {
            call.response.headers.append("ETag", ManifestEtag)
            call.respondText(
                text =
                    """
                    {
                      "revision":"$ManifestRevision",
                      "generatedAt":"$ManifestGeneratedAt",
                      "pictogramHashes":{
                        "yes":"hash-yes-v1",
                        "no":"hash-no-v1"
                      }
                    }
                    """.trimIndent(),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.OK,
            )
        }

        get("/delta") {
            val since = call.request.queryParameters["since"].orEmpty()
            if (since.isBlank()) {
                call.respondText(
                    text = """{"error":"Missing required query parameter: since"}""",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.BadRequest,
                )
                return@get
            }

            val body =
                """
                {
                  "revision":"$ManifestRevision",
                  "upserts":[
                    {
                      "id":"yes",
                      "label":"Yes",
                      "spokenText":"Yes",
                      "imageRef":"yes.png",
                      "category":"core",
                      "sortOrder":1
                    },
                    {
                      "id":"no",
                      "label":"No",
                      "spokenText":"No",
                      "imageRef":"no.png",
                      "category":"core",
                      "sortOrder":2
                    }
                  ],
                  "deletes":[]
                }
                """.trimIndent()

            call.respondText(
                text = body,
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.OK,
            )
        }
    }
}
