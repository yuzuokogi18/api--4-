package com.equipxpress.routes

import com.equipxpress.domain.services.ItemsService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.itemsRoutes(itemsService: ItemsService) {

    route("/items") {
        get {
            call.respond(itemsService.getAll())
        }
    }
}
