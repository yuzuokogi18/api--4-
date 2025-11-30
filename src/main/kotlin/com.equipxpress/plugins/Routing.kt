package com.equipxpress.plugins

import com.equipxpress.infrastructure.adapters.input.rest.*
import com.equipxpress.application.usecases.users.*
import com.equipxpress.application.usecases.items.*
import com.equipxpress.application.usecases.requests.*
import com.equipxpress.application.usecases.prestamos.*
import com.equipxpress.application.usecases.categorias.*
import com.equipxpress.application.usecases.notifications.*
import com.equipxpress.application.usecases.roles.*
import com.equipxpress.application.usecases.userroles.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        // Users Use Cases
        val createUserUseCase: CreateUserUseCase by inject()
        val loginUserUseCase: LoginUserUseCase by inject()
        val getUserUseCase: GetUserUseCase by inject()
        val getAllUsersUseCase: GetAllUsersUseCase by inject()
        val updateUserUseCase: UpdateUserUseCase by inject()
        val deleteUserUseCase: DeleteUserUseCase by inject()
        
        // Items Use Cases
        val createItemUseCase: CreateItemUseCase by inject()
        val getItemsUseCase: GetItemsUseCase by inject()
        val updateItemUseCase: UpdateItemUseCase by inject()
        val deleteItemUseCase: DeleteItemUseCase by inject()
        
        // Requests Use Cases
        val createRequestUseCase: CreateRequestUseCase by inject()
        val getRequestsUseCase: GetRequestsUseCase by inject()
        val getAllRequestsUseCase: GetAllRequestsUseCase by inject()
        val getRequestByIdUseCase: GetRequestByIdUseCase by inject()
        val updateRequestUseCase: UpdateRequestUseCase by inject()
        val deleteRequestUseCase: DeleteRequestUseCase by inject()
        
        // Prestamos Use Cases
        val createPrestamoUseCase: CreatePrestamoUseCase by inject()
        val getAllPrestamosUseCase: GetAllPrestamosUseCase by inject()
        val getPrestamoByIdUseCase: GetPrestamoByIdUseCase by inject()
        val updatePrestamoUseCase: UpdatePrestamoUseCase by inject()
        val deletePrestamoUseCase: DeletePrestamoUseCase by inject()
        
        // Categorias Use Cases
        val createCategoriaUseCase: CreateCategoriaUseCase by inject()
        val getAllCategoriasUseCase: GetAllCategoriasUseCase by inject()
        val getCategoriaByIdUseCase: GetCategoriaByIdUseCase by inject()
        val updateCategoriaUseCase: UpdateCategoriaUseCase by inject()
        val deleteCategoriaUseCase: DeleteCategoriaUseCase by inject()
        
        // Notifications Use Cases
        val createNotificationUseCase: CreateNotificationUseCase by inject()
        val getAllNotificationsUseCase: GetAllNotificationsUseCase by inject()
        val getNotificationsByUserUseCase: GetNotificationsByUserUseCase by inject()
        val getNotificationByIdUseCase: GetNotificationByIdUseCase by inject()
        val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase by inject()
        val deleteNotificationUseCase: DeleteNotificationUseCase by inject()
        
        // Roles Use Cases
        val createRoleUseCase: CreateRoleUseCase by inject()
        val getAllRolesUseCase: GetAllRolesUseCase by inject()
        val getRoleByIdUseCase: GetRoleByIdUseCase by inject()
        val updateRoleUseCase: UpdateRoleUseCase by inject()
        val deleteRoleUseCase: DeleteRoleUseCase by inject()
        
        // UserRoles Use Cases
        val assignRoleToUserUseCase: AssignRoleToUserUseCase by inject()
        val getAllUserRolesUseCase: GetAllUserRolesUseCase by inject()
        val getUserRolesByUserIdUseCase: GetUserRolesByUserIdUseCase by inject()
        val getUserRolesByRoleIdUseCase: GetUserRolesByRoleIdUseCase by inject()
        val removeRoleFromUserUseCase: RemoveRoleFromUserUseCase by inject()
        val deleteUserRoleUseCase: DeleteUserRoleUseCase by inject()
        
        // Configurar rutas
        usersController(
            createUserUseCase,
            getUserUseCase,
            getAllUsersUseCase,
            updateUserUseCase,
            deleteUserUseCase,
            loginUserUseCase
        )
        
        itemsController(
            createItemUseCase,
            getItemsUseCase,
            updateItemUseCase,
            deleteItemUseCase
        )
        
        requestsController(
            createRequestUseCase,
            getRequestsUseCase,
            getAllRequestsUseCase,
            getRequestByIdUseCase,
            updateRequestUseCase,
            deleteRequestUseCase
        )
        
        prestamosController(
            createPrestamoUseCase,
            getAllPrestamosUseCase,
            getPrestamoByIdUseCase,
            updatePrestamoUseCase,
            deletePrestamoUseCase
        )
        
        categoriasController(
            createCategoriaUseCase,
            getAllCategoriasUseCase,
            getCategoriaByIdUseCase,
            updateCategoriaUseCase,
            deleteCategoriaUseCase
        )
        
        notificationsController(
            createNotificationUseCase,
            getAllNotificationsUseCase,
            getNotificationsByUserUseCase,
            getNotificationByIdUseCase,
            markNotificationAsReadUseCase,
            deleteNotificationUseCase
        )
        
        rolesController(
            createRoleUseCase,
            getAllRolesUseCase,
            getRoleByIdUseCase,
            updateRoleUseCase,
            deleteRoleUseCase
        )
        
        userRolesController(
            assignRoleToUserUseCase,
            getAllUserRolesUseCase,
            getUserRolesByUserIdUseCase,
            getUserRolesByRoleIdUseCase,
            removeRoleFromUserUseCase,
            deleteUserRoleUseCase
        )
    }
}