package es.gamearena.repository;

import es.gamearena.model.Participacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Acceso a las inscripciones y a sus consultas por jugador y torneo.
 *
 * @author Darius Joanito Marinescu
 */
public interface ParticipacionRepository
        extends JpaRepository<Participacion, Long> {

    /**
     * Comprueba si un jugador tiene alguna participación registrada.
     *
     * @param jugadorId identificador del jugador
     * @return {@code true} si tiene participaciones;
     *         {@code false} en caso contrario
     */
    boolean existsByJugadorId(Long jugadorId);

    /**
     * Comprueba si un torneo tiene alguna participación registrada.
     *
     * @param torneoId identificador del torneo
     * @return {@code true} si tiene participaciones;
     *         {@code false} en caso contrario
     */
    boolean existsByTorneoId(Long torneoId);

    /**
     * Busca las inscripciones de un jugador.
     *
     * @param jugadorId identificador del jugador
     * @return participaciones con el torneo de cada inscripción
     */
    List<Participacion> findByJugadorId(Long jugadorId);

    /**
     * Busca las inscripciones de un torneo.
     *
     * @param torneoId identificador del torneo
     * @return participaciones con el jugador de cada inscripción
     */
    List<Participacion> findByTorneoId(Long torneoId);
}