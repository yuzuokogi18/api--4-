package com.equipxpress.routes

import com.equipxpress.domain.services.PrestamosService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.prestamosRoutes(prestamosService: PrestamosService) {

    route("/prestamos") {

        post {
            val data = call.receive<Map<String, Any>>()
            call.respond(
                prestamosService.registrarPrestamo(
                    userId = data["userId"].toString().toInt(),
                    itemId = data["itemId"].toString().toInt()
                )
            )
        }
    }
}
