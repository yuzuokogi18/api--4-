package com.equipxpress.utils

import org.mindrot.jbcrypt.BCrypt

object Hashing {
    fun hash(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
    
    fun verify(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }
}