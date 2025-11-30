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
            try {
                val request = call.receive<Request>()
                val result = createRequestUseCase(request)
                
                result.onSuccess { createdRequest ->
                    logger.info("Solicitud creada: ID=${createdRequest.id}, userId=${createdRequest.userId}, itemId=${createdRequest.itemId}")
                    call.respond(HttpStatusCode.Created, createdRequest)
                }.onFailure { error ->
                    logger.error("Error al crear solicitud: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al crear solicitud"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al crear solicitud: ${e.message}", e)
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Datos inválidos: ${e.message}")
                )
            }
        }
        
        get {
            try {
                val result = getAllRequestsUseCase()
                
                result.onSuccess { requests ->
                    logger.debug("Obtenidas ${requests.size} solicitudes")
                    call.respond(HttpStatusCode.OK, requests)
                }.onFailure { error ->
                    logger.error("Error al obtener solicitudes: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener solicitudes"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener solicitudes: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
        
        get("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )
                
                val result = getRequestByIdUseCase(id)
                
                result.onSuccess { request ->
                    logger.debug("Solicitud encontrada: ID=$id")
                    call.respond(HttpStatusCode.OK, request)
                }.onFailure { error ->
                    logger.warn("Solicitud no encontrada: ID=$id")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Solicitud no encontrada"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener solicitud: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
        
        get("/user/{userId}") {
            try {
                val userId = call.parameters["userId"]?.toIntOrNull()
                    ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "User ID inválido")
                    )
                
                val result = getRequestsUseCase.getByUser(userId)
                
                result.onSuccess { requests ->
                    logger.debug("Obtenidas ${requests.size} solicitudes para userId=$userId")
                    call.respond(HttpStatusCode.OK, requests)
                }.onFailure { error ->
                    logger.error("Error al obtener solicitudes del usuario: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener solicitudes"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener solicitudes del usuario: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
        
        put("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@put call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )
                
                val request = call.receive<Request>()
                val result = updateRequestUseCase(id, request)
                
                result.onSuccess { updatedRequest ->
                    logger.info("Solicitud actualizada: ID=$id")
                    call.respond(HttpStatusCode.OK, updatedRequest)
                }.onFailure { error ->
                    logger.error("Error al actualizar solicitud: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al actualizar solicitud"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al actualizar solicitud: ${e.message}", e)
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Datos inválidos: ${e.message}")
                )
            }
        }
        
        delete("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@delete call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )
                
                val result = deleteRequestUseCase(id)
                
                result.onSuccess {
                    logger.info("Solicitud eliminada: ID=$id")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Solicitud eliminada correctamente"))
                }.onFailure { error ->
                    logger.warn("Error al eliminar solicitud: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Solicitud no encontrada"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al eliminar solicitud: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
    }
}