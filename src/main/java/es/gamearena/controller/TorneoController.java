package es.gamearena.controller;

import es.gamearena.model.EstadoTorneo;
import es.gamearena.model.Participacion;
import es.gamearena.model.Torneo;
import es.gamearena.service.ParticipacionService;
import es.gamearena.service.TorneoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Expone las operaciones de torneos y las consultas de filtros y participantes.
 * Permite peticiones desde otros orígenes para conectar el cliente con Live Server.
 *
 * @author Darius Joanito Marinescu
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/torneos")
public class TorneoController {

    /** Servicio de torneos. */
    private final TorneoService torneoService;

    /** Servicio de participaciones. */
    private final ParticipacionService participacionService;

    /**
     * Crea el controlador con los servicios que atienden sus peticiones.
     *
     * @param torneoService servicio de torneos
     * @param participacionService servicio de participaciones
     */
    public TorneoController(
            TorneoService torneoService,
            ParticipacionService participacionService) {

        this.torneoService = torneoService;
        this.participacionService = participacionService;
    }

    /**
     * Recibe el alta de un torneo.
     *
     * @param datos datos del torneo y el ID de su videojuego
     * @return torneo creado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Torneo crear(@RequestBody Torneo datos) {
        return torneoService.crear(datos);
    }

    /**
     * Lista los torneos aplicando los filtros opcionales recibidos en la URL.
     * Si se proporcionan ambos filtros, devuelve los torneos que cumplen los dos.
     * Si no se proporciona ninguno, devuelve todos los torneos.
     *
     * @param estado estado por el que se filtra,
     *               o {@code null} para no aplicarlo
     * @param videojuegoId identificador del videojuego por el que se filtra,
     *                    o {@code null} para no aplicarlo
     * @return lista de torneos que cumplen los filtros indicados
     * @throws org.springframework.web.server.ResponseStatusException con estado
     *         404 si se indica un videojuego que no existe
     */
    @GetMapping
    public List<Torneo> listar(
            @RequestParam(required = false) EstadoTorneo estado,
            @RequestParam(required = false) Long videojuegoId) {

        if (videojuegoId != null && estado != null) {
            return torneoService.listarPorVideojuegoYEstado(
                    videojuegoId,
                    estado
            );
        }

        if (videojuegoId != null) {
            return torneoService.listarPorVideojuego(videojuegoId);
        }

        if (estado != null) {
            return torneoService.listarPorEstado(estado);
        }

        return torneoService.listar();
    }

    /**
     * Consulta un torneo por ID.
     *
     * @param id identificador del torneo
     * @return torneo encontrado
     */
    @GetMapping("/{id}")
    public Torneo buscarPorId(@PathVariable Long id) {
        return torneoService.buscarPorId(id);
    }

    /**
     * Sustituye los datos de un torneo existente.
     *
     * @param id identificador del torneo
     * @param datos nuevos datos del torneo
     * @return torneo actualizado
     */
    @PutMapping("/{id}")
    public Torneo modificar(
            @PathVariable Long id,
            @RequestBody Torneo datos) {

        return torneoService.modificar(id, datos);
    }

    /**
     * Solicita la eliminación de un torneo sin participaciones.
     * Si la eliminación se completa, devuelve HTTP 204 sin cuerpo.
     *
     * @param id identificador del torneo
     * @throws org.springframework.web.server.ResponseStatusException con estado
     *         404 si el torneo no existe, o con estado 409 si tiene participaciones
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        torneoService.eliminar(id);
    }

    /**
     * Consulta los torneos en un estado determinado.
     *
     * @param estado estado que deben tener los torneos
     * @return torneos con ese estado
     */
    @GetMapping("/estado/{estado}")
    public List<Torneo> listarPorEstado(
            @PathVariable EstadoTorneo estado) {

        return torneoService.listarPorEstado(estado);
    }

    /**
     * Consulta los torneos de un videojuego.
     *
     * @param id identificador del videojuego
     * @return torneos del videojuego
     */
    @GetMapping("/videojuego/{id}")
    public List<Torneo> listarPorVideojuego(@PathVariable Long id) {
        return torneoService.listarPorVideojuego(id);
    }

    /**
     * Consulta los torneos que cumplen los dos filtros a la vez.
     *
     * @param id identificador del videojuego
     * @param estado estado que deben tener los torneos
     * @return torneos del videojuego con el estado indicado
     */
    @GetMapping("/videojuego/{id}/estado/{estado}")
    public List<Torneo> listarPorVideojuegoYEstado(
            @PathVariable Long id,
            @PathVariable EstadoTorneo estado) {

        return torneoService.listarPorVideojuegoYEstado(id, estado);
    }

    /**
     * Consulta las participaciones de un torneo, incluyendo el jugador asociado
     * a cada inscripción.
     * Acepta las rutas {@code /torneos/{id}/participantes}
     * y {@code /torneos/{id}/participaciones}.
     *
     * @param id identificador del torneo
     * @return lista de participaciones del torneo,
     *         vacía si no tiene inscripciones
     * @throws org.springframework.web.server.ResponseStatusException con estado
     *         404 si el torneo no existe
     */
    @GetMapping({"/{id}/participantes", "/{id}/participaciones"})
    public List<Participacion> listarParticipantes(@PathVariable Long id) {
        return participacionService.listarPorTorneo(id);
    }

    /**
     * Inscribe a un jugador en el torneo identificado en la URL.
     * El torneo se obtiene mediante el identificador de la ruta y se asigna
     * a los datos recibidos antes de registrar la participación.
     * Si la inscripción se completa, devuelve HTTP 201 y la participación creada.
     *
     * @param id identificador del torneo
     * @param datos participación recibida, con el identificador del jugador,
     *              los puntos y la posición
     * @return participación creada con el jugador y el torneo asociados
     * @throws org.springframework.web.server.ResponseStatusException con estado
     *         400 si falta el identificador del jugador, o con estado 404 si el
     *         jugador o el torneo no existen
     */
    @PostMapping("/{id}/participaciones")
    @ResponseStatus(HttpStatus.CREATED)
    public Participacion inscribir(
            @PathVariable Long id,
            @RequestBody Participacion datos) {

        datos.setTorneo(torneoService.buscarPorId(id));

        return participacionService.inscribir(datos);
    }
}