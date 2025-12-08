package com.equipxpress.infrastructure.adapters.input.rest

import com.equipxpress.application.usecases.requests.*
import com.equipxpress.domain.models.Request
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("RequestsController")

fun Route.requestsController(
    createRequestUseCase: CreateRequestUseCase,
    getRequestsUseCase: GetRequestsUseCase,
    getAllRequestsUseCase: GetAllRequestsUseCase,
    getRequestByIdUseCase: GetRequestByIdUseCase,
    updateRequestUseCase: UpdateRequestUseCase,
    deleteRequestUseCase: DeleteRequestUseCase
) {
    route("/api/requests") {

        post {
            val req = call.receive<Request>()
            val result = createRequestUseCase(req)

            result.onSuccess { created ->
                logger.info("Solicitud creada ID=${created.id}")
                call.respond(HttpStatusCode.Created, created)
            }.onFailure {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to it.message))
            }
        }

        get {
            val result = getAllRequestsUseCase()
            result.onSuccess { call.respond(it) }
                .onFailure { call.respond(HttpStatusCode.InternalServerError, mapOf("error" to it.message)) }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))

            val result = getRequestByIdUseCase(id)
            result.onSuccess { call.respond(it) }
                .onFailure { call.respond(HttpStatusCode.NotFound, mapOf("error" to it.message)) }
        }

        get("/user/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "UserID inválido"))

            val result = getRequestsUseCase.getByUser(userId)
            result.onSuccess { call.respond(it) }
                .onFailure { call.respond(HttpStatusCode.InternalServerError, mapOf("error" to it.message)) }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))

            val req = call.receive<Request>()
            val result = updateRequestUseCase(id, req)

            result.onSuccess { call.respond(it) }
                .onFailure { call.respond(HttpStatusCode.BadRequest, mapOf("error" to it.message)) }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))

            val result = deleteRequestUseCase(id)
            result.onSuccess { call.respond(mapOf("message" to "Solicitud eliminada")) }
                .onFailure { call.respond(HttpStatusCode.NotFound, mapOf("error" to it.message)) }
        }
    }
}
