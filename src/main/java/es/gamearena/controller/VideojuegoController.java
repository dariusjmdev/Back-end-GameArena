package es.gamearena.controller;

import es.gamearena.model.Videojuego;
import es.gamearena.service.VideojuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Expone el alta y las consultas de videojuegos.
 * Permite peticiones desde otros orígenes para conectar el cliente con Live Server.
 *
 * @author Darius Joanito Marinescu
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/videojuegos")
public class VideojuegoController {

    /** Servicio de videojuegos. */
    private final VideojuegoService videojuegoService;

    /**
     * Crea el controlador con el servicio de videojuegos.
     *
     * @param videojuegoService servicio de videojuegos
     */
    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    /**
     * Recibe el alta de un videojuego.
     *
     * @param datos nombre y género del nuevo videojuego
     * @return videojuego creado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Videojuego crear(@RequestBody Videojuego datos) {
        return videojuegoService.crear(datos);
    }

    /**
     * Lista los videojuegos registrados.
     *
     * @return videojuegos registrados
     */
    @GetMapping
    public List<Videojuego> listar() {
        return videojuegoService.listar();
    }

    /**
     * Consulta un videojuego por ID.
     *
     * @param id identificador del videojuego
     * @return videojuego encontrado
     */
    @GetMapping("/{id}")
    public Videojuego buscarPorId(@PathVariable Long id) {
        return videojuegoService.buscarPorId(id);
    }
}