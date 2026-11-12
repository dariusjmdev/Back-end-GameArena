package es.gamearena.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Competición de un videojuego con fecha, premio y estado.
 *
 * @author Darius Joanito Marinescu
 */
@Entity
public class Torneo {

    /** Identificador de la entidad. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del torneo. */
    @Column(nullable = false)
    private String nombre;

    /** Fecha del torneo. */
    @Column(nullable = false)
    private LocalDate fecha;

    /** Importe del premio. */
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal premio;

    /** Estado del torneo. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTorneo estado;

    /** Videojuego en el que se disputa el torneo. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "videojuego_id", nullable = false)
    private Videojuego videojuego;

    /** Participaciones del torneo, cuya existencia impide su eliminación. */
    @JsonIgnore
    @OneToMany(mappedBy = "torneo")
    private List<Participacion> participaciones = new ArrayList<>();

    /**
     * Crea una instancia vacía para JPA y para recibir los datos de una petición.
     */
    public Torneo() {
    }

    /**
     * Crea un torneo con los datos indicados.
     *
     * @param nombre nombre del torneo
     * @param fecha fecha del torneo
     * @param premio importe del premio
     * @param estado estado del torneo
     * @param videojuego videojuego en el que se disputa el torneo
     */
    public Torneo(
            String nombre,
            LocalDate fecha,
            BigDecimal premio,
            EstadoTorneo estado,
            Videojuego videojuego) {

        this.nombre = nombre;
        this.fecha = fecha;
        this.premio = premio;
        this.estado = estado;
        this.videojuego = videojuego;
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
     * Obtiene el valor de {@code nombre}.
     *
     * @return nombre del torneo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor de {@code nombre}.
     *
     * @param nombre nombre del torneo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el valor de {@code fecha}.
     *
     * @return fecha del torneo
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece el valor de {@code fecha}.
     *
     * @param fecha fecha del torneo
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el valor de {@code premio}.
     *
     * @return importe del premio
     */
    public BigDecimal getPremio() {
        return premio;
    }

    /**
     * Establece el valor de {@code premio}.
     *
     * @param premio importe del premio
     */
    public void setPremio(BigDecimal premio) {
        this.premio = premio;
    }

    /**
     * Obtiene el valor de {@code estado}.
     *
     * @return estado del torneo
     */
    public EstadoTorneo getEstado() {
        return estado;
    }

    /**
     * Establece el valor de {@code estado}.
     *
     * @param estado estado del torneo
     */
    public void setEstado(EstadoTorneo estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el valor de {@code videojuego}.
     *
     * @return videojuego en el que se disputa el torneo
     */
    public Videojuego getVideojuego() {
        return videojuego;
    }

    /**
     * Establece el valor de {@code videojuego}.
     *
     * @param videojuego videojuego en el que se disputa el torneo
     */
    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    /**
     * Obtiene la relación inversa gestionada por JPA.
     *
     * @return participaciones asociadas a esta entidad
     */
    public List<Participacion> getParticipaciones() {
        return participaciones;
    }
}