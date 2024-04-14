package com.stayscape.backend.domain.security

import com.stayscape.backend.domain.user.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*


@Service
class JwtService(
    @Value("\${app.jwt}")
    private var secretKey: String
) {

    fun extractUsername(jwtToken: String): String? {
        return extractClaim(jwtToken, Claims::getSubject)
    }

    fun extractUserId(jwtToken: String): Int? {
        val allClaims = extractAllClaims(jwtToken)
        return allClaims.get("user_id", Integer::class.java).toInt()
    }

    fun extractUserRole(jwtToken: String): String? {
        val allClaims = extractAllClaims(jwtToken)
        return allClaims.get("role", String::class.java)
    }

    fun extractAllClaims(jwtToken: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(jwtToken)
            .body
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }

    fun isTokenValid(jwtToken: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(jwtToken)
        return username == userDetails.username && !isTokenExpired(jwtToken)

    }

    fun extractExpiration(jwtToken: String): Date? {
        return extractClaim(jwtToken, Claims::getExpiration)
    }

    private fun isTokenExpired(jwtToken: String): Boolean {
        return extractExpiration(jwtToken)?.before(Date()) ?: false
    }

    fun generateConfirmToken(email: String): String {
        return generateToken(email = email, timeMillis = 1000L * 60 * 60 * 24 * 30)
    }


    fun generateResetPasswordToken(email: String): String {
        return generateToken(email = email, timeMillis = 1000 * 60 * 60 * 24)
    }

    fun generateAccessToken(user: User): String {
        val claims = HashMap<String, Any>()
        claims["role"] = "${user.role}"
        claims["user_id"] = user.id!!
        return generateToken(claims, user.email!!, 1000 * 60 * 10)
    }

    fun generateRefreshToken(email: String): String {
        return generateToken(email = email, timeMillis = 1000 * 60 * 60 * 24)
    }

    fun generateToken(extraClaims: Map<String, Any> = emptyMap(), email: String, timeMillis: Long): String {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(email)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + timeMillis))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getSignInKey(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }
}
