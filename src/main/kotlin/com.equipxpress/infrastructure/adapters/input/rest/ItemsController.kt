package com.equipxpress.infrastructure.adapters.input.rest

import com.equipxpress.application.usecases.items.*
import com.equipxpress.domain.models.Item
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("ItemsController")

fun Route.itemsController(
    createItemUseCase: CreateItemUseCase,
    getItemsUseCase: GetItemsUseCase,
    updateItemUseCase: UpdateItemUseCase,
    deleteItemUseCase: DeleteItemUseCase
) {
    route("/api/items") {
        post {
            try {
                val item = call.receive<Item>()
                val result = createItemUseCase(item)
                
                result.onSuccess { createdItem ->
                    logger.info("Item creado exitosamente: ID=${createdItem.id}, nombre=${createdItem.nombre}")
                    call.respond(HttpStatusCode.Created, createdItem)
                }.onFailure { error ->
                    logger.error("Error al crear item: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al crear item"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al crear item: ${e.message}", e)
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Datos inválidos: ${e.message}")
                )
            }
        }
        
        get {
            try {
                val result = getItemsUseCase.getAll()
                
                result.onSuccess { items ->
                    logger.debug("Obtenidos ${items.size} items")
                    call.respond(HttpStatusCode.OK, items)
                }.onFailure { error ->
                    logger.error("Error al obtener items: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener items"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener items: ${e.message}", e)
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
                
                val result = getItemsUseCase.getById(id)
                
                result.onSuccess { item ->
                    logger.debug("Item encontrado: ID=$id")
                    call.respond(HttpStatusCode.OK, item)
                }.onFailure { error ->
                    logger.warn("Item no encontrado: ID=$id")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Item no encontrado"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener item: ${e.message}", e)
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
                
                val item = call.receive<Item>()
                val result = updateItemUseCase(id, item)
                
                result.onSuccess { updatedItem ->
                    logger.info("Item actualizado: ID=$id")
                    call.respond(HttpStatusCode.OK, updatedItem)
                }.onFailure { error ->
                    logger.error("Error al actualizar item: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al actualizar item"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al actualizar item: ${e.message}", e)
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
                
                val result = deleteItemUseCase(id)
                
                result.onSuccess {
                    logger.info("Item eliminado: ID=$id")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Item eliminado correctamente"))
                }.onFailure { error ->
                    logger.warn("Error al eliminar item: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Item no encontrado"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al eliminar item: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
    }
}