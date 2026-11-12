package es.gamearena.repository;

import es.gamearena.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a los jugadores almacenados en H2.
 *
 * @author Darius Joanito Marinescu
 */
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}
