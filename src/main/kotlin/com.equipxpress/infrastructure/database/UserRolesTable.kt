package com.equipxpress.infrastructure.database

import org.jetbrains.exposed.sql.Table

object UserRolesTable : Table("user_roles") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val roleId = integer("role_id").references(RolesTable.id)
    
    override val primaryKey = PrimaryKey(id)
}