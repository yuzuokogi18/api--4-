package com.equipxpress.application.users

import com.equipxpress.domain.models.User
import com.equipxpress.domain.services.UserService

class LoginUserUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(correo: String, password: String): User? {
        return userService.login(correo, password)
    }
}
