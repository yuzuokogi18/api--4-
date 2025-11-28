package com.equipxpress.application.users

import com.equipxpress.domain.models.User
import com.equipxpress.domain.services.UserService

class GetUserUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(id: Int): User? {
        return userService.getUserById(id)
    }
}
