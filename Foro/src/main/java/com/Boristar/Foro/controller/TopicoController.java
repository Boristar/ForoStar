package com.Boristar.Foro.controller;

import com.Boristar.Foro.dto.*;
import com.Boristar.Foro.model.Curso;
import com.Boristar.Foro.model.Topico;
import com.Boristar.Foro.model.Usuario;
import com.Boristar.Foro.repository.CursoRepository;
import com.Boristar.Foro.repository.TopicoRepository;
import com.Boristar.Foro.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private static final Logger logger = LoggerFactory.getLogger(TopicoController.class);

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriBuilder) {
        logger.info("LOG DE DEBUG: Recibida petición para registrar un nuevo tópico: {}", datos.titulo());
        Usuario autor = usuarioRepository.findById(datos.idUsuario()).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        Curso curso = cursoRepository.findById(datos.idCurso()).orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        Topico topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        topicoRepository.save(topico);
        logger.info("LOG DE DEBUG: Tópico registrado con ID: {}", topico.getId());

        DatosRespuestaTopico respuesta = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getAutor().getLogin(), topico.getCurso().getNombre());
        URI url = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        logger.info("LOG DE DEBUG: Recibida petición para listar tópicos paginados.");
        Page<DatosListadoTopico> pagina = topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> detallarTopico(@PathVariable Long id) {
        logger.info("LOG DE DEBUG: Recibida petición para detallar el tópico con ID: {}", id);
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        DatosRespuestaTopico respuesta = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getAutor().getLogin(), topico.getCurso().getNombre());
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datos) {
        logger.info("LOG DE DEBUG: Recibida petición para actualizar el tópico con ID: {}", datos.id());
        Topico topico = topicoRepository.findById(datos.id()).orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        topico.actualizarDatos(datos);
        logger.info("LOG DE DEBUG: Tópico con ID {} actualizado.", topico.getId());
        DatosRespuestaTopico respuesta = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getAutor().getLogin(), topico.getCurso().getNombre());
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        logger.info("LOG DE DEBUG: Recibida petición para eliminar (desactivar) el tópico con ID: {}", id);
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        topico.desactivarTopico();
        logger.info("LOG DE DEBUG: Tópico con ID {} desactivado.", topico.getId());
        return ResponseEntity.noContent().build();
    }
}