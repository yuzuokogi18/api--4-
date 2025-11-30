package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    
    fun init() {
        // Configuración para PostgreSQL
        val jdbcURL = "jdbc:postgresql://localhost:5434/equipxpress"
        val driverClassName = "org.postgresql.Driver"
        val user = "postgres"
        val password = "root"  // ← Cambia esto por tu contraseña real
        
        Database.connect(
            url = jdbcURL,
            driver = driverClassName,
            user = user,
            password = password
        )
        
        transaction {
            SchemaUtils.create(
                UserTable,
                RolesTable,
                UserRolesTable,
                CategoriasTable,
                ItemsTable,
                RequestsTable,
                HistorialPrestamosTable,
                NotificationsTable
            )
            
            // Insertar datos iniciales
            insertInitialData()
        }
    }
    
    private fun insertInitialData() {
        // Insertar roles si no existen
        if (RolesTable.selectAll().count() == 0L) {
            RolesTable.insert {
                it[nombre] = "administrador"
            }
            RolesTable.insert {
                it[nombre] = "alumno"
            }
        }
        
        // Insertar categorías si no existen
        if (CategoriasTable.selectAll().count() == 0L) {
            CategoriasTable.insert {
                it[nombre] = "Electrónico"
            }
            CategoriasTable.insert {
                it[nombre] = "Laboratorio"
            }
            CategoriasTable.insert {
                it[nombre] = "Uso escolar"
            }
        }
    }
}