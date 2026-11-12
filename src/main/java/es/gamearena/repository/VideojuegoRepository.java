package es.gamearena.repository;

import es.gamearena.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a los videojuegos almacenados en H2.
 *
 * @author Darius Joanito Marinescu
 */
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {
}
