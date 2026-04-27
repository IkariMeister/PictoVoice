package com.pictovoice.server.routes

import com.pictovoice.server.auth.requireCaregiverAuth
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.caregiverRoutes() {
    route("/v1/caregiver") {
        put("/layout") {
            if (!call.requireCaregiverAuth()) return@put

            call.respondText(
                text = """{"revision":"revision-2"}""",
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.OK,
            )
        }
    }
}
