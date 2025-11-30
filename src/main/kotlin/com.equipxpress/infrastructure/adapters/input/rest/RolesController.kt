package com.equipxpress.infrastructure.adapters.input.rest

import com.equipxpress.application.usecases.roles.*
import com.equipxpress.domain.models.Role
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("RolesController")

fun Route.rolesController(
    createRoleUseCase: CreateRoleUseCase,
    getAllRolesUseCase: GetAllRolesUseCase,
    getRoleByIdUseCase: GetRoleByIdUseCase,
    updateRoleUseCase: UpdateRoleUseCase,
    deleteRoleUseCase: DeleteRoleUseCase
) {
    route("/api/roles") {
        post {
            try {
                val role = call.receive<Role>()
                val result = createRoleUseCase(role)
                
                result.onSuccess { createdRole ->
                    logger.info("Rol creado exitosamente: ID=${createdRole.id}, nombre=${createdRole.nombre}")
                    call.respond(HttpStatusCode.Created, createdRole)
                }.onFailure { error ->
                    logger.error("Error al crear rol: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al crear rol"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al crear rol: ${e.message}", e)
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Datos inválidos: ${e.message}")
                )
            }
        }
        
        get {
            try {
                val result = getAllRolesUseCase()
                
                result.onSuccess { roles ->
                    logger.debug("Obtenidos ${roles.size} roles")
                    call.respond(HttpStatusCode.OK, roles)
                }.onFailure { error ->
                    logger.error("Error al obtener roles: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener roles"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener roles: ${e.message}", e)
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
                
                val result = getRoleByIdUseCase(id)
                
                result.onSuccess { role ->
                    logger.debug("Rol encontrado: ID=$id")
                    call.respond(HttpStatusCode.OK, role)
                }.onFailure { error ->
                    logger.warn("Rol no encontrado: ID=$id")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Role no encontrado"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener rol: ${e.message}", e)
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
                
                val role = call.receive<Role>()
                val result = updateRoleUseCase(id, role)
                
                result.onSuccess { updatedRole ->
                    logger.info("Rol actualizado: ID=$id")
                    call.respond(HttpStatusCode.OK, updatedRole)
                }.onFailure { error ->
                    logger.error("Error al actualizar rol: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al actualizar rol"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al actualizar rol: ${e.message}", e)
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
                
                val result = deleteRoleUseCase(id)
                
                result.onSuccess {
                    logger.info("Rol eliminado: ID=$id")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Role eliminado correctamente"))
                }.onFailure { error ->
                    logger.warn("Error al eliminar rol: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Role no encontrado"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al eliminar rol: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
    }
}