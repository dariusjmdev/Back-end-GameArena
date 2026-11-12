package es.gamearena.controller;

import es.gamearena.model.Jugador;
import es.gamearena.model.Participacion;
import es.gamearena.service.JugadorService;
import es.gamearena.service.ParticipacionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Expone las operaciones de jugadores y la consulta de sus participaciones.
 * Permite peticiones desde otros orígenes para conectar el cliente con Live Server.
 *
 * @author Darius Joanito Marinescu
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/jugadores")
public class JugadorController {

    /** Servicio de jugadores. */
    private final JugadorService jugadorService;

    /** Servicio de participaciones. */
    private final ParticipacionService participacionService;

    /**
     * Crea el controlador con los servicios que atienden sus peticiones.
     *
     * @param jugadorService servicio de jugadores
     * @param participacionService servicio de participaciones
     */
    public JugadorController(
            JugadorService jugadorService,
            ParticipacionService participacionService) {

        this.jugadorService = jugadorService;
        this.participacionService = participacionService;
    }

    /**
     * Recibe el alta de un jugador.
     *
     * @param datos datos del nuevo jugador
     * @return jugador creado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Jugador crear(@RequestBody Jugador datos) {
        return jugadorService.crear(datos);
    }

    /**
     * Lista los jugadores registrados.
     *
     * @return jugadores registrados
     */
    @GetMapping
    public List<Jugador> listar() {
        return jugadorService.listar();
    }

    /**
     * Consulta un jugador por ID.
     *
     * @param id identificador del jugador
     * @return jugador encontrado
     */
    @GetMapping("/{id}")
    public Jugador buscarPorId(@PathVariable Long id) {
        return jugadorService.buscarPorId(id);
    }

    /**
     * Sustituye los datos de un jugador existente.
     *
     * @param id identificador del jugador
     * @param datos nuevo nick, email y nivel
     * @return jugador actualizado
     */
    @PutMapping("/{id}")
    public Jugador modificar(
            @PathVariable Long id,
            @RequestBody Jugador datos) {

        return jugadorService.modificar(id, datos);
    }

    /**
     * Solicita la eliminación de un jugador sin participaciones.
     * Si la eliminación se completa, devuelve HTTP 204 sin cuerpo.
     *
     * @param id identificador del jugador
     * @throws org.springframework.web.server.ResponseStatusException con estado
     *         404 si el jugador no existe, o con estado 409 si tiene participaciones
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        jugadorService.eliminar(id);
    }

    /**
     * Consulta las participaciones y los torneos del jugador.
     *
     * @param id identificador del jugador
     * @return participaciones con el torneo asociado
     */
    @GetMapping("/{id}/participaciones")
    public List<Participacion> listarParticipaciones(@PathVariable Long id) {
        return participacionService.listarPorJugador(id);
    }
}