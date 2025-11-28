package com.equipxpress.config

import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain
import com.equipxpress.routes.*
import com.equipxpress.infrastructure.database.DatabaseFactory
import org.koin.ktor.plugin.Koin
import org.koin.ktor.ext.inject
import com.equipxpress.config.KoinModules
import com.equipxpress.domain.services.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {

    DatabaseFactory.init()

    install(Koin) {
        modules(KoinModules.modules)
    }

    val usersService by inject<UserService>()
    val itemsService by inject<ItemsService>()
    val requestsService by inject<RequestsService>()
    val prestamosService by inject<PrestamosService>()

    routing {
        usersRoutes(usersService)
        itemsRoutes(itemsService)
        requestsRoutes(requestsService)
        prestamosRoutes(prestamosService)
    }
}
