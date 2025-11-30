package com.equipxpress.application.usecases.users

import com.equipxpress.domain.repositories.UserRepository
import com.equipxpress.utils.Hashing
import com.equipxpress.utils.JWTConfig
import kotlinx.serialization.Serializable

data class LoginRequest(
    val correo: String,
    val password: String
)

@Serializable  // ← AGREGADO
data class LoginResponse(
    val token: String,
    val userId: Int,
    val nombre: String,
    val correo: String,
    val rol: String
)

class LoginUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(request: LoginRequest): Result<LoginResponse> {
        return try {
            val user = userRepository.findByCorreo(request.correo)
                ?: return Result.failure(Exception("Credenciales incorrectas"))
            
            if (!Hashing.verify(request.password, user.password)) {
                return Result.failure(Exception("Credenciales incorrectas"))
            }
            
            val token = JWTConfig.generateToken(user.id!!)
            
            val response = LoginResponse(
                token = token,
                userId = user.id,
                nombre = user.nombre,
                correo = user.correo,
                rol = user.rol
            )
            
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}