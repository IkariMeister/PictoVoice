package com.pictovoice.server.routes

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private val requestJson = Json { ignoreUnknownKeys = true }
private val supportedPlatforms = setOf("android", "ios")

fun Route.deviceRoutes() {
    route("/v1/devices") {
        post("/register") {
            val rawBody = call.receiveText()
            val requestJsonObject =
                runCatching { requestJson.parseToJsonElement(rawBody).jsonObject }
                    .getOrElse {
                        call.respondText(
                            text = """{"error":"Invalid JSON body"}""",
                            contentType = ContentType.Application.Json,
                            status = HttpStatusCode.BadRequest,
                        )
                        return@post
                    }

            val platform = requestJsonObject["platform"]?.jsonPrimitive?.contentOrNull.orEmpty()
            val token = requestJsonObject["token"]?.jsonPrimitive?.contentOrNull.orEmpty()

            if (platform !in supportedPlatforms || token.isBlank()) {
                call.respondText(
                    text = """{"error":"Invalid registration payload"}""",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.BadRequest,
                )
                return@post
            }

            call.respond(HttpStatusCode.NoContent)
        }
    }
}
