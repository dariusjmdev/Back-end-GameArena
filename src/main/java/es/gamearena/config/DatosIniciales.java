package es.gamearena.config;

import es.gamearena.model.EstadoTorneo;
import es.gamearena.model.Jugador;
import es.gamearena.model.Participacion;
import es.gamearena.model.Torneo;
import es.gamearena.model.Videojuego;
import es.gamearena.repository.JugadorRepository;
import es.gamearena.repository.ParticipacionRepository;
import es.gamearena.repository.TorneoRepository;
import es.gamearena.repository.VideojuegoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Prepara los datos mínimos de GameArena cuando se crea la base de datos.
 *
 * @author Darius Joanito Marinescu
 */
@Component
public class DatosIniciales implements CommandLineRunner {

    /** Repositorio de jugadores. */
    private final JugadorRepository jugadorRepository;

    /** Repositorio de videojuegos. */
    private final VideojuegoRepository videojuegoRepository;

    /** Repositorio de torneos. */
    private final TorneoRepository torneoRepository;

    /** Repositorio de participaciones. */
    private final ParticipacionRepository participacionRepository;

    /**
     * Crea el componente con los repositorios necesarios para guardar los datos.
     *
     * @param jugadorRepository repositorio de jugadores
     * @param videojuegoRepository repositorio de videojuegos
     * @param torneoRepository repositorio de torneos
     * @param participacionRepository repositorio de participaciones
     */
    public DatosIniciales(JugadorRepository jugadorRepository, VideojuegoRepository videojuegoRepository,
                          TorneoRepository torneoRepository, ParticipacionRepository participacionRepository) {
        this.jugadorRepository = jugadorRepository;
        this.videojuegoRepository = videojuegoRepository;
        this.torneoRepository = torneoRepository;
        this.participacionRepository = participacionRepository;
    }

    /**
     * Guarda tres videojuegos, cinco jugadores, cinco torneos y diez participaciones.
     * La carga se realiza en una sola transacción cuando aún no hay videojuegos.
     *
     * @param args argumentos de inicio de la aplicación
     */
    @Override
    @Transactional
    public void run(String... args) {
        if (videojuegoRepository.count() > 0) {
            return;
        }

        Videojuego valorant = videojuegoRepository.save(new Videojuego("Valorant", "Disparos tácticos"));
        Videojuego leagueOfLegends = videojuegoRepository.save(new Videojuego("League of Legends", "MOBA"));
        Videojuego rocketLeague = videojuegoRepository.save(new Videojuego("Rocket League", "Deportes"));

        Jugador lince = jugadorRepository.save(new Jugador("Lince", "lince@example.test", "Avanzado"));
        Jugador pixel = jugadorRepository.save(new Jugador("Pixel", "pixel@example.test", "Intermedio"));
        Jugador nova = jugadorRepository.save(new Jugador("Nova", "nova@example.test", "Avanzado"));
        Jugador turbo = jugadorRepository.save(new Jugador("Turbo", "turbo@example.test", "Intermedio"));
        Jugador sombra = jugadorRepository.save(new Jugador("Sombra", "sombra@example.test", "Principiante"));

        Torneo valorantAbierto = torneoRepository.save(new Torneo("Copa Valorant", LocalDate.of(2027, 1, 23),
                new BigDecimal("500.00"), EstadoTorneo.ABIERTO, valorant));
        Torneo valorantFinalizado = torneoRepository.save(new Torneo("Valorant de Otoño", LocalDate.of(2026, 10, 17),
                new BigDecimal("350.00"), EstadoTorneo.FINALIZADO, valorant));
        Torneo copaInvocadores = torneoRepository.save(new Torneo("Copa de Invocadores", LocalDate.of(2026, 11, 9),
                new BigDecimal("450.00"), EstadoTorneo.EN_CURSO, leagueOfLegends));
        Torneo ligaRocket = torneoRepository.save(new Torneo("Liga Rocket", LocalDate.of(2027, 2, 6),
                new BigDecimal("300.00"), EstadoTorneo.ABIERTO, rocketLeague));
        Torneo rocketFinalizado = torneoRepository.save(new Torneo("Rocket de Otoño", LocalDate.of(2026, 10, 24),
                new BigDecimal("250.00"), EstadoTorneo.FINALIZADO, rocketLeague));

        participacionRepository.saveAll(List.of(
                new Participacion(lince, valorantAbierto, 0, 0),
                new Participacion(pixel, valorantAbierto, 0, 0),
                new Participacion(lince, valorantFinalizado, 18, 1),
                new Participacion(nova, valorantFinalizado, 12, 2),
                new Participacion(nova, copaInvocadores, 9, 1),
                new Participacion(turbo, copaInvocadores, 6, 2),
                new Participacion(turbo, ligaRocket, 0, 0),
                new Participacion(sombra, ligaRocket, 0, 0),
                new Participacion(pixel, rocketFinalizado, 15, 1),
                new Participacion(sombra, rocketFinalizado, 10, 2)
        ));
    }
}
