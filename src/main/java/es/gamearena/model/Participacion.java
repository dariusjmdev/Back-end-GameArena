package es.gamearena.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Inscripción de un jugador en un torneo con sus puntos y posición.
 *
 * @author Darius Joanito Marinescu
 */
@Entity
public class Participacion {

    /** Identificador de la entidad. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Jugador inscrito. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    /** Torneo en el que se inscribe el jugador. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "torneo_id", nullable = false)
    private Torneo torneo;

    /** Puntos obtenidos por el jugador. */
    @Column(nullable = false)
    private int puntos;

    /** Posición obtenida por el jugador, con cero cuando aún no tiene puesto. */
    @Column(nullable = false)
    private int posicion;

    /**
     * Crea una instancia vacía para JPA y para recibir los datos de una petición.
     */
    public Participacion() {
    }

    /**
     * Crea una inscripción con los datos indicados.
     *
     * @param jugador jugador inscrito
     * @param torneo torneo en el que se inscribe el jugador
     * @param puntos puntos obtenidos por el jugador
     * @param posicion posición obtenida por el jugador, con cero cuando aún no tiene puesto
     */
    public Participacion(Jugador jugador, Torneo torneo, int puntos, int posicion) {
        this.jugador = jugador;
        this.torneo = torneo;
        this.puntos = puntos;
        this.posicion = posicion;
    }

    /**
     * Obtiene el valor de {@code id}.
     *
     * @return identificador de la entidad
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el valor de {@code jugador}.
     *
     * @return jugador inscrito
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Establece el valor de {@code jugador}.
     *
     * @param jugador jugador inscrito
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /**
     * Obtiene el valor de {@code torneo}.
     *
     * @return torneo en el que se inscribe el jugador
     */
    public Torneo getTorneo() {
        return torneo;
    }

    /**
     * Establece el valor de {@code torneo}.
     *
     * @param torneo torneo en el que se inscribe el jugador
     */
    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    /**
     * Obtiene el valor de {@code puntos}.
     *
     * @return puntos obtenidos por el jugador
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Establece el valor de {@code puntos}.
     *
     * @param puntos puntos obtenidos por el jugador
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Obtiene el valor de {@code posicion}.
     *
     * @return posición obtenida por el jugador, con cero cuando aún no tiene puesto
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * Establece el valor de {@code posicion}.
     *
     * @param posicion posición obtenida por el jugador, con cero cuando aún no tiene puesto
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
