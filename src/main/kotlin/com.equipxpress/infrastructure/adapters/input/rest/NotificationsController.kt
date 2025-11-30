package com.equipxpress.infrastructure.adapters.input.rest

import com.equipxpress.application.usecases.notifications.*
import com.equipxpress.domain.models.Notification
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("NotificationsController")

fun Route.notificationsController(
    createNotificationUseCase: CreateNotificationUseCase,
    getAllNotificationsUseCase: GetAllNotificationsUseCase,
    getNotificationsByUserUseCase: GetNotificationsByUserUseCase,
    getNotificationByIdUseCase: GetNotificationByIdUseCase,
    markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    deleteNotificationUseCase: DeleteNotificationUseCase
) {
    route("/api/notifications") {
        post {
            try {
                val notification = call.receive<Notification>()
                val result = createNotificationUseCase(notification)
                
                result.onSuccess { createdNotification ->
                    logger.info("Notificación creada: ID=${createdNotification.id}, userId=${createdNotification.userId}")
                    call.respond(HttpStatusCode.Created, createdNotification)
                }.onFailure { error ->
                    logger.error("Error al crear notificación: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al crear notificación"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al crear notificación: ${e.message}", e)
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Datos inválidos: ${e.message}")
                )
            }
        }
        
        get {
            try {
                val result = getAllNotificationsUseCase()
                
                result.onSuccess { notifications ->
                    logger.debug("Obtenidas ${notifications.size} notificaciones")
                    call.respond(HttpStatusCode.OK, notifications)
                }.onFailure { error ->
                    logger.error("Error al obtener notificaciones: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener notificaciones"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener notificaciones: ${e.message}", e)
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
                
                val result = getNotificationByIdUseCase(id)
                
                result.onSuccess { notification ->
                    logger.debug("Notificación encontrada: ID=$id")
                    call.respond(HttpStatusCode.OK, notification)
                }.onFailure { error ->
                    logger.warn("Notificación no encontrada: ID=$id")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Notificación no encontrada"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener notificación: ${e.message}", e)
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
                
                val result = getNotificationsByUserUseCase(userId)
                
                result.onSuccess { notifications ->
                    logger.debug("Obtenidas ${notifications.size} notificaciones para userId=$userId")
                    call.respond(HttpStatusCode.OK, notifications)
                }.onFailure { error ->
                    logger.error("Error al obtener notificaciones del usuario: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener notificaciones"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener notificaciones del usuario: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
        
        patch("/{id}/read") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@patch call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )
                
                val result = markNotificationAsReadUseCase(id)
                
                result.onSuccess {
                    logger.info("Notificación marcada como leída: ID=$id")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Notificación marcada como leída"))
                }.onFailure { error ->
                    logger.warn("Error al marcar notificación como leída: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Notificación no encontrada"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al marcar notificación como leída: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
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
                
                val result = deleteNotificationUseCase(id)
                
                result.onSuccess {
                    logger.info("Notificación eliminada: ID=$id")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Notificación eliminada correctamente"))
                }.onFailure { error ->
                    logger.warn("Error al eliminar notificación: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Notificación no encontrada"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al eliminar notificación: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
    }
}