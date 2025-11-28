package com.equipxpress.application.users

import com.equipxpress.domain.models.User
import com.equipxpress.domain.services.UserService


class CreateUserUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(
        nombre: String,
        correo: String,
        password: String,
        rol: String
    ): User {
        return userService.createUser(nombre, correo, password, rol)
    }
}
