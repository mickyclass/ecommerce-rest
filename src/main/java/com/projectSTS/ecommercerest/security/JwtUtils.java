package com.projectSTS.ecommercerest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    // Clave secreta fija para firmar los tokens
    // En producción se configura en application.properties
    private static final String SECRET = "ECommerceRestSuperSecretKeyForJWTAuthSystem2026";
    
    // Tiempo de expiración del token
    private static final long EXPIRATION_TIME = 86400000;

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 1. Generar token con username y sus roles
    public String generarToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. Obtener el usuario (username) a partir del token
    public String obtenerUsernameDelToken(String token) {
        return obtenerClaims(token).getSubject();
    }

    // 3. Obtener los roles guardados dentro del token
    @SuppressWarnings("unchecked")
    public List<String> obtenerRolesDelToken(String token) {
        return obtenerClaims(token).get("roles", List.class);
    }

    // 4. Validar si el token es correcto y no ha caducado
    public boolean validarToken(String token) {
        try {
            obtenerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Método auxiliar privado para leer el contenido del token
    private Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}