package com.equipxpress.infrastructure.adapters.input.rest

import com.equipxpress.application.usecases.userroles.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("UserRolesController")

fun Route.userRolesController(
    assignRoleToUserUseCase: AssignRoleToUserUseCase,
    getAllUserRolesUseCase: GetAllUserRolesUseCase,
    getUserRolesByUserIdUseCase: GetUserRolesByUserIdUseCase,
    getUserRolesByRoleIdUseCase: GetUserRolesByRoleIdUseCase,
    removeRoleFromUserUseCase: RemoveRoleFromUserUseCase,
    deleteUserRoleUseCase: DeleteUserRoleUseCase
) {
    route("/api/user-roles") {
        post {
            try {
                val data = call.receive<Map<String, Int>>()
                
                val userId = data["userId"] ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "userId es requerido")
                )
                val roleId = data["roleId"] ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "roleId es requerido")
                )
                
                val result = assignRoleToUserUseCase(userId, roleId)
                
                result.onSuccess { userRole ->
                    logger.info("Rol asignado a usuario: userRoleId=${userRole.id}, userId=$userId, roleId=$roleId")
                    call.respond(HttpStatusCode.Created, userRole)
                }.onFailure { error ->
                    logger.error("Error al asignar rol a usuario: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al asignar rol"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al asignar rol a usuario: ${e.message}", e)
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Datos inválidos: ${e.message}")
                )
            }
        }
        
        get {
            try {
                val result = getAllUserRolesUseCase()
                
                result.onSuccess { userRoles ->
                    logger.debug("Obtenidas ${userRoles.size} relaciones user-role")
                    call.respond(HttpStatusCode.OK, userRoles)
                }.onFailure { error ->
                    logger.error("Error al obtener user-roles: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener user-roles"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener user-roles: ${e.message}", e)
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
                
                val result = getUserRolesByUserIdUseCase(userId)
                
                result.onSuccess { userRoles ->
                    logger.debug("Obtenidos ${userRoles.size} roles para userId=$userId")
                    call.respond(HttpStatusCode.OK, userRoles)
                }.onFailure { error ->
                    logger.error("Error al obtener roles del usuario: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener roles del usuario"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener roles del usuario: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
        
        get("/role/{roleId}") {
            try {
                val roleId = call.parameters["roleId"]?.toIntOrNull()
                    ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Role ID inválido")
                    )
                
                val result = getUserRolesByRoleIdUseCase(roleId)
                
                result.onSuccess { userRoles ->
                    logger.debug("Obtenidos ${userRoles.size} usuarios con roleId=$roleId")
                    call.respond(HttpStatusCode.OK, userRoles)
                }.onFailure { error ->
                    logger.error("Error al obtener usuarios con este rol: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener usuarios con este rol"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener usuarios con rol: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
        
        delete("/user/{userId}/role/{roleId}") {
            try {
                val userId = call.parameters["userId"]?.toIntOrNull()
                    ?: return@delete call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "User ID inválido")
                    )
                
                val roleId = call.parameters["roleId"]?.toIntOrNull()
                    ?: return@delete call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Role ID inválido")
                    )
                
                val result = removeRoleFromUserUseCase(userId, roleId)
                
                result.onSuccess {
                    logger.info("Rol removido de usuario: userId=$userId, roleId=$roleId")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Rol removido correctamente"))
                }.onFailure { error ->
                    logger.warn("Error al remover rol de usuario: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Relación no encontrada"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al remover rol de usuario: ${e.message}", e)
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
                
                val result = deleteUserRoleUseCase(id)
                
                result.onSuccess {
                    logger.info("UserRole eliminado: ID=$id")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "UserRole eliminado correctamente"))
                }.onFailure { error ->
                    logger.warn("Error al eliminar UserRole: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "UserRole no encontrado"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al eliminar UserRole: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
    }
}