package es.gamearena.service;

import es.gamearena.model.Videojuego;
import es.gamearena.repository.VideojuegoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Valida el alta de videojuegos y recupera los videojuegos registrados.
 *
 * @author Darius Joanito Marinescu
 */
@Service
public class VideojuegoService {

    /** Repositorio de videojuegos. */
    private final VideojuegoRepository videojuegoRepository;

    /**
     * Crea el servicio con su repositorio.
     *
     * @param videojuegoRepository repositorio de videojuegos
     */
    public VideojuegoService(VideojuegoRepository videojuegoRepository) {
        this.videojuegoRepository = videojuegoRepository;
    }

    /**
     * Registra un videojuego con su nombre y género.
     *
     * @param datos nombre y género del nuevo videojuego
     * @return videojuego guardado
     * @throws ResponseStatusException si el nombre o el género están vacíos
     */
    public Videojuego crear(Videojuego datos) {
        if (datos.getNombre() == null || datos.getNombre().isBlank()
                || datos.getGenero() == null || datos.getGenero().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre y género son obligatorios");
        }

        Videojuego videojuego = new Videojuego(datos.getNombre(), datos.getGenero());
        return videojuegoRepository.save(videojuego);
    }

    /**
     * Recupera todos los videojuegos.
     *
     * @return videojuegos almacenados
     */
    public List<Videojuego> listar() {
        return videojuegoRepository.findAll();
    }

    /**
     * Busca un videojuego por su identificador.
     *
     * @param id identificador del videojuego
     * @return videojuego encontrado
     * @throws ResponseStatusException si el videojuego no existe
     */
    public Videojuego buscarPorId(Long id) {
        return videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe el videojuego con ID " + id));
    }
}
