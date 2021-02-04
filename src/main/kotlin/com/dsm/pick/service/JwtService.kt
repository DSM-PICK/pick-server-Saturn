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
    private val securityKey: String,
) {
    private val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(securityKey)
    private val signatureAlgorithm = SignatureAlgorithm.HS256
    private val key = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)

    fun createToken(teacherId: String, tokenType: Token): String =
        Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject("access token")
            .claim("id", teacherId)
            .setExpiration(Date(System.currentTimeMillis() + tokenType.expirationTime))
            .signWith(signatureAlgorithm, key)
            .compact()
}