package com.equipxpress.infrastructure.adapters.input.rest

import com.equipxpress.application.usecases.categorias.*
import com.equipxpress.domain.models.Categoria
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("CategoriasController")

fun Route.categoriasController(
    createCategoriaUseCase: CreateCategoriaUseCase,
    getAllCategoriasUseCase: GetAllCategoriasUseCase,
    getCategoriaByIdUseCase: GetCategoriaByIdUseCase,
    updateCategoriaUseCase: UpdateCategoriaUseCase,
    deleteCategoriaUseCase: DeleteCategoriaUseCase
) {
    route("/api/categorias") {
        post {
            try {
                val categoria = call.receive<Categoria>()
                val result = createCategoriaUseCase(categoria)
                
                result.onSuccess { createdCategoria ->
                    logger.info("Categoría creada exitosamente: ID=${createdCategoria.id}")
                    call.respond(HttpStatusCode.Created, createdCategoria)
                }.onFailure { error ->
                    logger.error("Error al crear categoría: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al crear categoría"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al crear categoría: ${e.message}", e)
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Datos inválidos: ${e.message}")
                )
            }
        }
        
        get {
            try {
                val result = getAllCategoriasUseCase()
                
                result.onSuccess { categorias ->
                    logger.debug("Obtenidas ${categorias.size} categorías")
                    call.respond(HttpStatusCode.OK, categorias)
                }.onFailure { error ->
                    logger.error("Error al obtener categorías: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener categorías"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener categorías: ${e.message}", e)
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
                
                val result = getCategoriaByIdUseCase(id)
                
                result.onSuccess { categoria ->
                    logger.debug("Categoría encontrada: ID=$id")
                    call.respond(HttpStatusCode.OK, categoria)
                }.onFailure { error ->
                    logger.warn("Categoría no encontrada: ID=$id")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Categoría no encontrada"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener categoría: ${e.message}", e)
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
                
                val categoria = call.receive<Categoria>()
                val result = updateCategoriaUseCase(id, categoria)
                
                result.onSuccess { updatedCategoria ->
                    logger.info("Categoría actualizada: ID=$id")
                    call.respond(HttpStatusCode.OK, updatedCategoria)
                }.onFailure { error ->
                    logger.error("Error al actualizar categoría: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al actualizar categoría"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al actualizar categoría: ${e.message}", e)
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
                
                val result = deleteCategoriaUseCase(id)
                
                result.onSuccess {
                    logger.info("Categoría eliminada: ID=$id")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Categoría eliminada correctamente"))
                }.onFailure { error ->
                    logger.warn("Error al eliminar categoría: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Categoría no encontrada"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al eliminar categoría: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
    }
}