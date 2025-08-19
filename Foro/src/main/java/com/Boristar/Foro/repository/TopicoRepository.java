package com.Boristar.Foro.repository;

import com.Boristar.Foro.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Busca solo los tópicos que no han sido borrados lógicamente
    Page<Topico> findByActivoTrue(Pageable paginacion);
}