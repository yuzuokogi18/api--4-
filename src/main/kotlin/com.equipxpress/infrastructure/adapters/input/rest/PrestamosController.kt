package com.equipxpress.infrastructure.adapters.input.rest

import com.equipxpress.application.usecases.prestamos.*
import com.equipxpress.domain.models.HistorialPrestamo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("PrestamosController")

fun Route.prestamosController(
    createPrestamoUseCase: CreatePrestamoUseCase,
    getAllPrestamosUseCase: GetAllPrestamosUseCase,
    getPrestamoByIdUseCase: GetPrestamoByIdUseCase,
    updatePrestamoUseCase: UpdatePrestamoUseCase,
    deletePrestamoUseCase: DeletePrestamoUseCase
) {
    route("/api/prestamos") {
        post {
            try {
                val prestamo = call.receive<HistorialPrestamo>()
                val result = createPrestamoUseCase(prestamo)
                
                result.onSuccess { createdPrestamo ->
                    logger.info("Préstamo creado exitosamente: ID=${createdPrestamo.id}")
                    call.respond(HttpStatusCode.Created, createdPrestamo)
                }.onFailure { error ->
                    logger.error("Error al crear préstamo: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al registrar préstamo"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al crear préstamo: ${e.message}", e)
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Datos inválidos: ${e.message}")
                )
            }
        }
        
        get {
            try {
                val result = getAllPrestamosUseCase()
                
                result.onSuccess { prestamos ->
                    logger.debug("Obtenidos ${prestamos.size} préstamos")
                    call.respond(HttpStatusCode.OK, prestamos)
                }.onFailure { error ->
                    logger.error("Error al obtener préstamos: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener préstamos"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener préstamos: ${e.message}", e)
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
                
                val result = getPrestamoByIdUseCase(id)
                
                result.onSuccess { prestamo ->
                    logger.debug("Préstamo encontrado: ID=$id")
                    call.respond(HttpStatusCode.OK, prestamo)
                }.onFailure { error ->
                    logger.warn("Préstamo no encontrado: ID=$id")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Préstamo no encontrado"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener préstamo ID: ${e.message}", e)
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
                
                val prestamo = call.receive<HistorialPrestamo>()
                val result = updatePrestamoUseCase(id, prestamo)
                
                result.onSuccess { updatedPrestamo ->
                    logger.info("Préstamo actualizado: ID=$id")
                    call.respond(HttpStatusCode.OK, updatedPrestamo)
                }.onFailure { error ->
                    logger.error("Error al actualizar préstamo ID=$id: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al actualizar préstamo"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al actualizar préstamo ID: ${e.message}", e)
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
                
                val result = deletePrestamoUseCase(id)
                
                result.onSuccess {
                    logger.info("Préstamo eliminado: ID=$id")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Préstamo eliminado correctamente"))
                }.onFailure { error ->
                    logger.warn("Error al eliminar préstamo ID=$id: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Préstamo no encontrado"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al eliminar préstamo ID: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
    }
}