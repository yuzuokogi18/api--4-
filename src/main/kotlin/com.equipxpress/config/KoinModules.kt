package com.equipxpress.config

import com.equipxpress.domain.repositories.*
import com.equipxpress.domain.services.*
import com.equipxpress.infrastructure.repositoriesImpl.*
import org.koin.dsl.module

object KoinModules {

    val modules = module {

        val itemsRepository = ItemsRepositoryImpl()
    val itemsService = ItemsService(itemsRepository)

    val prestamosRepo = PrestamosRepositoryImpl()
    val prestamosService = PrestamosService(prestamosRepo)

    routing {
        itemsRoutes(itemsService)       
        prestamosRoutes(prestamosService)
        
    }

    }
}
