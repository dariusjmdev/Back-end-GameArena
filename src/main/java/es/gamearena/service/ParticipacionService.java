package es.gamearena.service;

import es.gamearena.model.Jugador;
import es.gamearena.model.Participacion;
import es.gamearena.model.Torneo;
import es.gamearena.repository.ParticipacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Gestiona la inscripción de jugadores y las consultas de participaciones.
 *
 * @author Darius Joanito Marinescu
 */
@Service
public class ParticipacionService {

    /** Repositorio de participaciones. */
    private final ParticipacionRepository participacionRepository;

    /** Servicio que permite localizar al jugador que se inscribe. */
    private final JugadorService jugadorService;

    /** Servicio que permite localizar el torneo en el que se realiza la inscripción. */
    private final TorneoService torneoService;

    /**
     * Crea el servicio con el repositorio y los servicios de las entidades relacionadas.
     *
     * @param participacionRepository repositorio de participaciones
     * @param jugadorService servicio de jugadores
     * @param torneoService servicio de torneos
     */
    public ParticipacionService(ParticipacionRepository participacionRepository,
                                JugadorService jugadorService, TorneoService torneoService) {
        this.participacionRepository = participacionRepository;
        this.jugadorService = jugadorService;
        this.torneoService = torneoService;
    }

    /**
     * Inscribe a un jugador existente en un torneo existente.
     * Los puntos y la posición valen cero si se omiten al inscribirse.
     *
     * @param datos referencias al jugador y al torneo, y datos de la inscripción
     * @return participación guardada
     * @throws ResponseStatusException si falta una referencia o una de las entidades no existe
     */
    public Participacion inscribir(Participacion datos) {
        if (datos.getJugador() == null || datos.getJugador().getId() == null
                || datos.getTorneo() == null || datos.getTorneo().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los ID de jugador y torneo son obligatorios");
        }

        Jugador jugador = jugadorService.buscarPorId(datos.getJugador().getId());
        Torneo torneo = torneoService.buscarPorId(datos.getTorneo().getId());
        Participacion participacion = new Participacion(jugador, torneo, datos.getPuntos(), datos.getPosicion());
        return participacionRepository.save(participacion);
    }

    /**
     * Consulta las participaciones de un jugador, incluido el torneo de cada una.
     *
     * @param jugadorId identificador del jugador
     * @return participaciones del jugador
     * @throws ResponseStatusException si el jugador no existe
     */
    public List<Participacion> listarPorJugador(Long jugadorId) {
        jugadorService.buscarPorId(jugadorId);
        return participacionRepository.findByJugadorId(jugadorId);
    }

    /**
     * Consulta las participaciones de un torneo, incluido el jugador de cada una.
     *
     * @param torneoId identificador del torneo
     * @return participaciones del torneo
     * @throws ResponseStatusException si el torneo no existe
     */
    public List<Participacion> listarPorTorneo(Long torneoId) {
        torneoService.buscarPorId(torneoId);
        return participacionRepository.findByTorneoId(torneoId);
    }
}
