package com.teamsparta.restudy.util.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Long,
    @Value("168") private val refreshTokenExpirationHour: Long,
) {

    fun validateToken(jwt: String): Result<Jws<Claims>>{
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }
    fun generateAccessToken(subject: String, nickname: String, role: String): String {
        return generateToken(subject, nickname, role, Duration.ofHours(accessTokenExpirationHour))
    }

    fun generateRefreshToken(subject: String, nickname: String, role: String): String {
        return generateToken(subject, nickname, role, Duration.ofHours(refreshTokenExpirationHour))
    }

    private fun generateToken(subject: String, nickname: String, role: String, expirationPeriod: Duration): String{
        val  claims: Claims = Jwts.claims().add(mapOf("role" to role, "nickname" to nickname)).build()

        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}