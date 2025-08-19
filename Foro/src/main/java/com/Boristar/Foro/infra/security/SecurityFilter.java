package com.Boristar.Foro.infra.security;

import com.Boristar.Foro.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        logger.info("LOG DE DEBUG: SecurityFilter ejecutándose para la petición: {} {}", request.getMethod(), request.getRequestURI());

        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            logger.info("LOG DE DEBUG: Se encontró cabecera de autenticación 'Bearer'.");
            var token = authHeader.replace("Bearer ", "");
            var subject = tokenService.getSubject(token);
            if (subject != null) {
                UserDetails usuario = usuarioRepository.findByLogin(subject);
                if (usuario != null) {
                    logger.info("LOG DE DEBUG: Usuario '{}' autenticado. Estableciendo contexto de seguridad.", subject);
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } else {
            logger.info("LOG DE DEBUG: No se encontró cabecera 'Bearer' en la petición.");
        }
        filterChain.doFilter(request, response);
    }
}