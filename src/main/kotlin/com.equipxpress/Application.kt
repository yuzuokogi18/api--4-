package com.equipxpress

import com.equipxpress.infrastructure.database.DatabaseFactory
import com.equipxpress.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    // Configurar base de datos
    DatabaseFactory.init()
    
    // Configurar serialización JSON
    install(ContentNegotiation) {
        json()
    }
    
    // Configurar Koin (inyección de dependencias)
    configureKoin()
    
    // Configurar rutas
    configureRouting()
}