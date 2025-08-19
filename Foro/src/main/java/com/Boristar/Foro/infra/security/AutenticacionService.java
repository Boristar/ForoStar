package com.Boristar.Foro.infra.security;

import com.Boristar.Foro.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacionService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("LOG DE DEBUG: Buscando usuario en la base de datos: '{}'", username);
        UserDetails usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            logger.warn("LOG DE DEBUG: Usuario no encontrado: '{}'", username);
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        logger.info("LOG DE DEBUG: Usuario encontrado: '{}'", username);
        return usuario;
    }
}