package com.Boristar.Foro.infra.security;

import com.Boristar.Foro.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        logger.info("LOG DE DEBUG: Generando token para el usuario '{}'", usuario.getLogin());
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("Foro API")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            logger.error("Error al generar token JWT", exception);
            throw new RuntimeException("Error al generar token JWT");
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Token no puede ser nulo");
        }
        DecodedJWT verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("Foro API")
                    .build()
                    .verify(token);
            logger.info("LOG DE DEBUG: Token verificado correctamente para el subject '{}'", verifier.getSubject());
            return verifier.getSubject();
        } catch (JWTVerificationException exception) {
            logger.warn("LOG DE DEBUG: Falló la verificación del token JWT. Razón: {}", exception.getMessage());
            return null; // Opcional: podrías lanzar una excepción si prefieres
        }
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00")); // Ajusta tu zona horaria
    }
}