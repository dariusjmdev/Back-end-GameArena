package es.gamearena.service;

import es.gamearena.model.EstadoTorneo;
import es.gamearena.model.Torneo;
import es.gamearena.model.Videojuego;
import es.gamearena.repository.TorneoRepository;
import es.gamearena.repository.ParticipacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Gestiona los torneos, comprueba el videojuego asociado y ejecuta los filtros.
 *
 * @author Darius Joanito Marinescu
 */
@Service
public class TorneoService {

    /** Repositorio de torneos. */
    private final TorneoRepository torneoRepository;

    /** Servicio que permite localizar el videojuego de un torneo. */
    private final VideojuegoService videojuegoService;

    /** Repositorio utilizado para comprobar las participaciones del torneo. */
    private final ParticipacionRepository participacionRepository;

    /**
     * Crea el servicio con las dependencias necesarias para gestionar torneos,
     * localizar sus videojuegos y comprobar sus participaciones.
     *
     * @param torneoRepository repositorio de torneos
     * @param videojuegoService servicio de videojuegos
     * @param participacionRepository repositorio de participaciones
     */
    public TorneoService(
            TorneoRepository torneoRepository,
            VideojuegoService videojuegoService,
            ParticipacionRepository participacionRepository) {

        this.torneoRepository = torneoRepository;
        this.videojuegoService = videojuegoService;
        this.participacionRepository = participacionRepository;
    }

    /**
     * Crea un torneo para un videojuego registrado.
     *
     * @param datos datos del torneo y referencia al videojuego
     * @return torneo guardado
     * @throws ResponseStatusException si faltan datos o el videojuego no existe
     */
    public Torneo crear(Torneo datos) {
        return guardar(new Torneo(), datos);
    }

    /**
     * Recupera todos los torneos.
     *
     * @return torneos almacenados
     */
    public List<Torneo> listar() {
        return torneoRepository.findAll();
    }

    /**
     * Busca un torneo por su identificador.
     *
     * @param id identificador del torneo
     * @return torneo encontrado
     * @throws ResponseStatusException si el torneo no existe
     */
    public Torneo buscarPorId(Long id) {
        return torneoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe el torneo con ID " + id
                ));
    }

    /**
     * Actualiza un torneo existente y su videojuego asociado.
     *
     * @param id identificador del torneo que se modifica
     * @param datos nuevos datos del torneo
     * @return torneo actualizado
     * @throws ResponseStatusException si faltan datos,
     *         o el torneo o el videojuego no existen
     */
    public Torneo modificar(Long id, Torneo datos) {
        return guardar(buscarPorId(id), datos);
    }

    /**
     * Elimina un torneo únicamente cuando no tiene participaciones registradas.
     * La comprobación y la eliminación se ejecutan dentro de una transacción.
     *
     * @param id identificador del torneo que se desea eliminar
     * @throws ResponseStatusException con estado 404 si el torneo no existe,
     *         o con estado 409 si tiene participaciones
     */
    @Transactional
    public void eliminar(Long id) {
        Torneo torneo = buscarPorId(id);

        if (participacionRepository.existsByTorneoId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el torneo porque tiene participaciones"
            );
        }

        torneoRepository.delete(torneo);
    }

    /**
     * Filtra los torneos por estado.
     *
     * @param estado estado que deben tener los torneos
     * @return torneos con ese estado
     */
    public List<Torneo> listarPorEstado(EstadoTorneo estado) {
        return torneoRepository.findByEstado(estado);
    }

    /**
     * Lista los torneos asociados a un videojuego.
     *
     * @param videojuegoId identificador del videojuego
     * @return torneos del videojuego
     * @throws ResponseStatusException si el videojuego no existe
     */
    public List<Torneo> listarPorVideojuego(Long videojuegoId) {
        videojuegoService.buscarPorId(videojuegoId);

        return torneoRepository.findByVideojuegoId(videojuegoId);
    }

    /**
     * Filtra los torneos que pertenecen al videojuego
     * y tienen el estado indicado.
     *
     * @param videojuegoId identificador del videojuego
     * @param estado estado que deben tener los torneos
     * @return torneos que cumplen ambos filtros
     * @throws ResponseStatusException si el videojuego no existe
     */
    public List<Torneo> listarPorVideojuegoYEstado(
            Long videojuegoId,
            EstadoTorneo estado) {

        videojuegoService.buscarPorId(videojuegoId);

        return torneoRepository.findByVideojuegoIdAndEstado(
                videojuegoId,
                estado
        );
    }

    /**
     * Valida los campos y asigna un videojuego existente antes de guardar.
     *
     * @param torneo registro nuevo o existente que se guarda
     * @param datos valores recibidos para sus campos
     * @return torneo guardado
     * @throws ResponseStatusException si faltan datos o el videojuego no existe
     */
    private Torneo guardar(Torneo torneo, Torneo datos) {
        if (datos.getNombre() == null || datos.getNombre().isBlank()
                || datos.getFecha() == null
                || datos.getPremio() == null
                || datos.getEstado() == null
                || datos.getVideojuego() == null
                || datos.getVideojuego().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nombre, fecha, premio, estado e ID de videojuego son obligatorios"
            );
        }

        Videojuego videojuego = videojuegoService.buscarPorId(
                datos.getVideojuego().getId()
        );

        torneo.setNombre(datos.getNombre());
        torneo.setFecha(datos.getFecha());
        torneo.setPremio(datos.getPremio());
        torneo.setEstado(datos.getEstado());
        torneo.setVideojuego(videojuego);

        return torneoRepository.save(torneo);
    }
}