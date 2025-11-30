package com.equipxpress.infrastructure.adapters.input.rest

import com.equipxpress.application.usecases.users.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("UsersController")

fun Route.usersController(
    createUserUseCase: CreateUserUseCase,
    getUserUseCase: GetUserUseCase,
    getAllUsersUseCase: GetAllUsersUseCase,
    updateUserUseCase: UpdateUserUseCase,
    deleteUserUseCase: DeleteUserUseCase,
    loginUserUseCase: LoginUserUseCase
) {
    route("/api/users") {
        post("/register") {
            try {
                val data = call.receive<Map<String, String>>()
                
                val nombre = data["nombre"] ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "El nombre es requerido")
                )
                val correo = data["correo"] ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "El correo es requerido")
                )
                val password = data["password"] ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "La contraseña es requerida")
                )
                val rol = data["rol"] ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "El rol es requerido")
                )
                
                val result = createUserUseCase(nombre, correo, password, rol)
                
                result.onSuccess { user ->
                    logger.info("Usuario registrado exitosamente: ID=${user.id}, correo=${user.correo}, rol=${user.rol}")
                    call.respond(HttpStatusCode.Created, user)
                }.onFailure { error ->
                    logger.error("Error al registrar usuario: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al crear usuario"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al registrar usuario: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
        
        post("/login") {
            try {
                val data = call.receive<Map<String, String>>()
                
                val correo = data["correo"] ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "El correo es requerido")
                )
                val password = data["password"] ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "La contraseña es requerida")
                )
                
                val loginRequest = LoginRequest(correo, password)
                val result = loginUserUseCase(loginRequest)
                
                result.onSuccess { response ->
                    logger.info("Login exitoso: correo=$correo")
                    call.respond(HttpStatusCode.OK, response)
                }.onFailure { error ->
                    logger.warn("Intento de login fallido: correo=$correo")
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        mapOf("error" to (error.message ?: "Credenciales incorrectas"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción en login: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor")
                )
            }
        }
        
        get {
            try {
                val result = getAllUsersUseCase()
                
                result.onSuccess { users ->
                    logger.debug("Obtenidos ${users.size} usuarios")
                    call.respond(HttpStatusCode.OK, users)
                }.onFailure { error ->
                    logger.error("Error al obtener usuarios: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to (error.message ?: "Error al obtener usuarios"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener usuarios: ${e.message}", e)
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
                
                val result = getUserUseCase.getById(id)
                
                result.onSuccess { user ->
                    logger.debug("Usuario encontrado: ID=$id")
                    call.respond(HttpStatusCode.OK, user)
                }.onFailure { error ->
                    logger.warn("Usuario no encontrado: ID=$id")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Usuario no encontrado"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al obtener usuario: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor")
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
                
                val data = call.receive<Map<String, String>>()
                
                val nombre = data["nombre"] ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "El nombre es requerido")
                )
                val correo = data["correo"] ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "El correo es requerido")
                )
                val password = data["password"]
                val rol = data["rol"] ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "El rol es requerido")
                )
                
                val result = updateUserUseCase(id, nombre, correo, password, rol)
                
                result.onSuccess { user ->
                    logger.info("Usuario actualizado: ID=$id")
                    call.respond(HttpStatusCode.OK, user)
                }.onFailure { error ->
                    logger.error("Error al actualizar usuario: ${error.message}", error)
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Error al actualizar usuario"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al actualizar usuario: ${e.message}", e)
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
                
                val result = deleteUserUseCase(id)
                
                result.onSuccess {
                    logger.info("Usuario eliminado: ID=$id")
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Usuario eliminado correctamente"))
                }.onFailure { error ->
                    logger.warn("Error al eliminar usuario: ${error.message}")
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Usuario no encontrado"))
                    )
                }
            } catch (e: Exception) {
                logger.error("Excepción al eliminar usuario: ${e.message}", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error del servidor: ${e.message}")
                )
            }
        }
    }
}