package com.dsm.pick.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

@Service
class JwtService(
    @Value("\${TOKEN_SECURE_KEY:kotlin-love}")
    private val secretKey: String,
) {
    private val base64SecretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    private val signatureAlgorithm = SignatureAlgorithm.HS256

    fun createToken(teacherId: String, tokenType: Token): String =
        Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .claim("id", teacherId)
            .setExpiration(Date(System.currentTimeMillis() + tokenType.expirationTime))
            .signWith(signatureAlgorithm, base64SecretKey)
            .compact()

    fun getTeacherId(token: String): String =
        Jwts.parser()
            .setSigningKey(base64SecretKey)
            .parseClaimsJws(token)
            .body
            .get("id", String::class.java)

    fun isValid(token: String) =
        try {
            val currentTime = Date()
            val expirationTime = Jwts.parser()
                .setSigningKey(base64SecretKey)
                .parseClaimsJws(token)
                .body
                .expiration
            expirationTime.after(currentTime)
        } catch (e: Exception) { false }
}
