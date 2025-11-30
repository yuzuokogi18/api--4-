package com.equipxpress.application.usecases.users

import com.equipxpress.domain.models.User
import com.equipxpress.domain.repositories.UserRepository
import com.equipxpress.utils.Hashing

class CreateUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(nombre: String, correo: String, password: String, rol: String): Result<User> {
        return try {
            // Validaciones
            if (correo.isBlank()) {
                return Result.failure(Exception("El correo es requerido"))
            }
            
            if (password.length < 6) {
                return Result.failure(Exception("La contraseña debe tener al menos 6 caracteres"))
            }
            
            // Verificar si ya existe
            val existingUser = userRepository.findByCorreo(correo)
            if (existingUser != null) {
                return Result.failure(Exception("El correo ya está registrado"))
            }
            
            // Hash de la contraseña
            val hashedPassword = Hashing.hash(password)
            val user = User(
                nombre = nombre,
                correo = correo,
                password = hashedPassword,
                rol = rol
            )
            
            // Crear usuario
            val createdUser = userRepository.create(user)
            Result.success(createdUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}