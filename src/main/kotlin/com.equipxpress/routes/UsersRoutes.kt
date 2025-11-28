package com.equipxpress.routes

import com.equipxpress.domain.services.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.usersRoutes(userService: UserService) {

    route("/users") {

        post("/register") {
            val data = call.receive<Map<String, String>>()
            val user = userService.createUser(
                data["nombre"]!!,
                data["correo"]!!,
                data["password"]!!,
                data["rol"]!!
            )
            call.respond(user)
        }

        post("/login") {
            val data = call.receive<Map<String, String>>()
            val user = userService.login(data["correo"]!!, data["password"]!!)
            if (user != null) call.respond(user)
            else call.respond("Credenciales incorrectas")
        }

        get("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val user = userService.getUserById(id)
            call.respond(user ?: "Usuario no encontrado")
        }
    }
}
