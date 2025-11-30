package com.equipxpress.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JWTConfig {
    
    private const val secret = "SUPER_SECRETO_123"
    private const val issuer = "equipxpress"
    private const val expiresIn = 1000L * 60L * 60L * 24L
    
    private val algorithm = Algorithm.HMAC256(secret)
    
    fun generateToken(userId: Int): String =
        JWT.create()
            .withIssuer(issuer)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
            .sign(algorithm)
    
    fun verifyToken(token: String): Int? {
        return try {
            val verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
            val jwt = verifier.verify(token)
            jwt.getClaim("userId").asInt()
        } catch (e: Exception) {
            null
        }
    }
}