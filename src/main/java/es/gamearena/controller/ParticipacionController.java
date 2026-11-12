package es.gamearena.controller;

import es.gamearena.model.Participacion;
import es.gamearena.service.ParticipacionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expone el endpoint para inscribir jugadores en torneos.
 * Permite peticiones desde otros orígenes para conectar el cliente con Live Server.
 *
 * @author Darius Joanito Marinescu
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/participaciones")
public class ParticipacionController {

    /** Servicio de participaciones. */
    private final ParticipacionService participacionService;

    /**
     * Crea el controlador con el servicio de participaciones.
     *
     * @param participacionService servicio de participaciones
     */
    public ParticipacionController(
            ParticipacionService participacionService) {

        this.participacionService = participacionService;
    }

    /**
     * Recibe la inscripción de un jugador en un torneo.
     *
     * @param datos datos de la inscripción,
     *              con el ID del jugador y del torneo
     * @return participación creada
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Participacion inscribir(@RequestBody Participacion datos) {
        return participacionService.inscribir(datos);
    }
}