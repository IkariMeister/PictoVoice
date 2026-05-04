package com.pictovoice.server.auth

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondText

const val CAREGIVER_DEV_TOKEN = "caregiver-dev-token"

suspend fun ApplicationCall.requireCaregiverAuth(): Boolean {
    val bearer = request.headers["Authorization"]?.removePrefix("Bearer ")?.trim()
    if (bearer == null) {
        respondText(
            text = """{"error":"Missing or invalid bearer token"}""",
            contentType = ContentType.Application.Json,
            status = HttpStatusCode.Unauthorized,
        )
        return false
    }

    if (bearer != CAREGIVER_DEV_TOKEN) {
        respondText(
            text = """{"error":"Unauthorized caregiver token"}""",
            contentType = ContentType.Application.Json,
            status = HttpStatusCode.Unauthorized,
        )
        return false
    }

    return true
}
