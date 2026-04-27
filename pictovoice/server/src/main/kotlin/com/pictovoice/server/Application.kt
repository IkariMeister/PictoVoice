package com.pictovoice.server

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import com.pictovoice.server.routes.vocabularyRoutes

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    routing {
        get("/health") {
            call.respondText(
                text = """{"status":"ok"}""",
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.OK,
            )
        }
        vocabularyRoutes()
    }
}
