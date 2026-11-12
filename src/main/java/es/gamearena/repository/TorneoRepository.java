package es.gamearena.repository;

import es.gamearena.model.EstadoTorneo;
import es.gamearena.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Acceso a los torneos y consultas derivadas sobre su estado y videojuego.
 *
 * @author Darius Joanito Marinescu
 */
public interface TorneoRepository extends JpaRepository<Torneo, Long> {

    /**
     * Busca los torneos que tienen el estado indicado.
     *
     * @param estado estado que deben tener los torneos
     * @return torneos con ese estado
     */
    List<Torneo> findByEstado(EstadoTorneo estado);

    /**
     * Busca los torneos asociados a un videojuego.
     *
     * @param videojuegoId identificador del videojuego
     * @return torneos del videojuego
     */
    List<Torneo> findByVideojuegoId(Long videojuegoId);

    /**
     * Busca los torneos de un videojuego que tienen un estado concreto.
     *
     * @param videojuegoId identificador del videojuego
     * @param estado estado que deben tener los torneos
     * @return torneos que cumplen ambos filtros
     */
    List<Torneo> findByVideojuegoIdAndEstado(Long videojuegoId, EstadoTorneo estado);
}
