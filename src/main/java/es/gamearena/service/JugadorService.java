package es.gamearena.service;

import es.gamearena.model.Jugador;
import es.gamearena.repository.JugadorRepository;
import es.gamearena.repository.ParticipacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Valida y gestiona el alta, la consulta, la modificación y la baja de jugadores.
 *
 * @author Darius Joanito Marinescu
 */
@Service
public class JugadorService {

    /** Repositorio de jugadores. */
    private final JugadorRepository jugadorRepository;

    /** Repositorio utilizado para comprobar las participaciones del jugador. */
    private final ParticipacionRepository participacionRepository;

    /**
     * Crea el servicio con los repositorios necesarios para gestionar jugadores
     * y comprobar sus participaciones.
     *
     * @param jugadorRepository repositorio de jugadores
     * @param participacionRepository repositorio de participaciones
     */
    public JugadorService(
            JugadorRepository jugadorRepository,
            ParticipacionRepository participacionRepository) {

        this.jugadorRepository = jugadorRepository;
        this.participacionRepository = participacionRepository;
    }

    /**
     * Da de alta un jugador con un identificador generado por la base de datos.
     *
     * @param datos nick, email y nivel del nuevo jugador
     * @return jugador guardado
     * @throws ResponseStatusException si faltan datos obligatorios
     */
    public Jugador crear(Jugador datos) {
        return guardar(new Jugador(), datos);
    }

    /**
     * Recupera todos los jugadores.
     *
     * @return jugadores almacenados
     */
    public List<Jugador> listar() {
        return jugadorRepository.findAll();
    }

    /**
     * Busca un jugador por su identificador.
     *
     * @param id identificador del jugador
     * @return jugador encontrado
     * @throws ResponseStatusException si el jugador no existe
     */
    public Jugador buscarPorId(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe el jugador con ID " + id
                ));
    }

    /**
     * Actualiza los datos de un jugador identificado por la ruta.
     *
     * @param id identificador del jugador que se modifica
     * @param datos nuevo nick, email y nivel
     * @return jugador actualizado
     * @throws ResponseStatusException si el jugador no existe
     *         o faltan datos obligatorios
     */
    public Jugador modificar(Long id, Jugador datos) {
        return guardar(buscarPorId(id), datos);
    }

    /**
     * Elimina un jugador únicamente cuando no tiene participaciones registradas.
     * La comprobación y la eliminación se ejecutan dentro de una transacción.
     *
     * @param id identificador del jugador que se desea eliminar
     * @throws ResponseStatusException con estado 404 si el jugador no existe,
     *         o con estado 409 si tiene participaciones
     */
    @Transactional
    public void eliminar(Long id) {
        Jugador jugador = buscarPorId(id);

        if (participacionRepository.existsByJugadorId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el jugador porque tiene participaciones"
            );
        }

        jugadorRepository.delete(jugador);
    }

    /**
     * Comprueba los campos del jugador y guarda el registro con esos valores.
     *
     * @param jugador registro nuevo o existente que se guarda
     * @param datos valores recibidos para sus campos
     * @return jugador guardado
     * @throws ResponseStatusException si el nick, el email o el nivel están vacíos
     */
    private Jugador guardar(Jugador jugador, Jugador datos) {
        if (datos.getNick() == null || datos.getNick().isBlank()
                || datos.getEmail() == null || datos.getEmail().isBlank()
                || datos.getNivel() == null || datos.getNivel().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nick, email y nivel son obligatorios"
            );
        }

        jugador.setNick(datos.getNick());
        jugador.setEmail(datos.getEmail());
        jugador.setNivel(datos.getNivel());

        return jugadorRepository.save(jugador);
    }
}