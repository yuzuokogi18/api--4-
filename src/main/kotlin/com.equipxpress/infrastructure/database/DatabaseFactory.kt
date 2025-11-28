package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/equipxpress",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "tu_password"
        )

        transaction {
            SchemaUtils.create(
                UserTable, ItemsTable, RequestsTable, PrestamosTable
            )
        }
    }
}
