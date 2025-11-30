package com.equipxpress.plugins

import com.equipxpress.domain.repositories.*
import com.equipxpress.infrastructure.repositoriesImpl.*
import com.equipxpress.application.usecases.users.*
import com.equipxpress.application.usecases.items.*
import com.equipxpress.application.usecases.requests.*
import com.equipxpress.application.usecases.prestamos.*
import com.equipxpress.application.usecases.categorias.*
import com.equipxpress.application.usecases.notifications.*
import com.equipxpress.application.usecases.userroles.*
import com.equipxpress.application.usecases.roles.*
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}

val appModule = module {
    // Repositories
    single<UserRepository> { UserRepositoryImpl() }
    single<ItemsRepository> { ItemsRepositoryImpl() }
    single<RequestsRepository> { RequestsRepositoryImpl() }
    single<PrestamosRepository> { PrestamosRepositoryImpl() }
    single<CategoriaRepository> { CategoriaRepositoryImpl() }
    single<NotificationRepository> { NotificationRepositoryImpl() }
    single<RoleRepository> { RoleRepositoryImpl() }
    single<UserRoleRepository> { UserRoleRepositoryImpl() }
    
    // Use Cases - Users
    single { CreateUserUseCase(get()) }
    single { LoginUserUseCase(get()) }
    single { GetUserUseCase(get()) }
    single { GetAllUsersUseCase(get()) }
    single { UpdateUserUseCase(get()) }
    single { DeleteUserUseCase(get()) }
    
    // Use Cases - Items
    single { CreateItemUseCase(get()) }
    single { GetItemsUseCase(get()) }
    single { UpdateItemUseCase(get()) }
    single { DeleteItemUseCase(get()) }
    
    // Use Cases - Requests
    single { CreateRequestUseCase(get(), get()) }
    single { GetRequestsUseCase(get()) }
    single { GetAllRequestsUseCase(get()) }
    single { GetRequestByIdUseCase(get()) }
    single { UpdateRequestUseCase(get()) }
    single { DeleteRequestUseCase(get()) }
    
    // Use Cases - Prestamos
    single { CreatePrestamoUseCase(get()) }
    single { GetAllPrestamosUseCase(get()) }
    single { GetPrestamoByIdUseCase(get()) }
    single { UpdatePrestamoUseCase(get()) }
    single { DeletePrestamoUseCase(get()) }
    
    // Use Cases - Categorias
    single { CreateCategoriaUseCase(get()) }
    single { GetAllCategoriasUseCase(get()) }
    single { GetCategoriaByIdUseCase(get()) }
    single { UpdateCategoriaUseCase(get()) }
    single { DeleteCategoriaUseCase(get()) }
    
    // Use Cases - Notifications
    single { CreateNotificationUseCase(get()) }
    single { GetAllNotificationsUseCase(get()) }
    single { GetNotificationsByUserUseCase(get()) }
    single { GetNotificationByIdUseCase(get()) }
    single { MarkNotificationAsReadUseCase(get()) }
    single { DeleteNotificationUseCase(get()) }
    
    // Use Cases - Roles
    single { CreateRoleUseCase(get()) }
    single { GetAllRolesUseCase(get()) }
    single { GetRoleByIdUseCase(get()) }
    single { UpdateRoleUseCase(get()) }
    single { DeleteRoleUseCase(get()) }
    
    // Use Cases - UserRoles
    single { AssignRoleToUserUseCase(get()) }
    single { GetAllUserRolesUseCase(get()) }
    single { GetUserRolesByUserIdUseCase(get()) }
    single { GetUserRolesByRoleIdUseCase(get()) }
    single { RemoveRoleFromUserUseCase(get()) }
    single { DeleteUserRoleUseCase(get()) }
}