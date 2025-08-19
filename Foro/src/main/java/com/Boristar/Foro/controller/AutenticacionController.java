package com.Boristar.Foro.controller;

import com.Boristar.Foro.dto.DatosAutenticacionUsuario;
import com.Boristar.Foro.dto.DatosJWTToken;
import com.Boristar.Foro.infra.security.TokenService;
import com.Boristar.Foro.model.Usuario;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacionController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        logger.info("LOG DE DEBUG: Intento de autenticación para el usuario '{}'", datos.login());
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        logger.info("LOG DE DEBUG: Autenticación exitosa para el usuario '{}'", datos.login());
        return ResponseEntity.ok(new DatosJWTToken(jwtToken));
    }
}

