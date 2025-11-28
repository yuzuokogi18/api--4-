package com.equipxpress.routes

import com.equipxpress.domain.services.RequestsService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.requestsRoutes(requestsService: RequestsService) {

    route("/requests") {

        post {
            val req = call.receive<Map<String, Int>>()
            val response = requestsService.createRequest(
                userId = req["userId"]!!,
                itemId = req["itemId"]!!
            )
            call.respond(response)
        }

        get("/{userId}") {
            val userId = call.parameters["userId"]!!.toInt()
            call.respond(requestsService.getByUser(userId))
        }
    }
}
