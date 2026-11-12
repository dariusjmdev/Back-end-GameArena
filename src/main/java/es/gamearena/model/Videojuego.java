package es.gamearena.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Videojuego en el que se disputan uno o varios torneos.
 *
 * @author Darius Joanito Marinescu
 */
@Entity
public class Videojuego {

    /** Identificador de la entidad. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del videojuego. */
    @Column(nullable = false)
    private String nombre;

    /** Género al que pertenece el videojuego. */
    @Column(nullable = false)
    private String genero;

    /** Torneos asociados al videojuego. */
    @JsonIgnore
    @OneToMany(mappedBy = "videojuego")
    private List<Torneo> torneos = new ArrayList<>();

    /**
     * Crea una instancia vacía para JPA y para recibir los datos de una petición.
     */
    public Videojuego() {
    }

    /**
     * Crea un videojuego con los datos indicados.
     *
     * @param nombre nombre del videojuego
     * @param genero género al que pertenece el videojuego
     */
    public Videojuego(String nombre, String genero) {
        this.nombre = nombre;
        this.genero = genero;
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
     * @return nombre del videojuego
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor de {@code nombre}.
     *
     * @param nombre nombre del videojuego
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el valor de {@code genero}.
     *
     * @return género al que pertenece el videojuego
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Establece el valor de {@code genero}.
     *
     * @param genero género al que pertenece el videojuego
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtiene la relación inversa gestionada por JPA.
     *
     * @return torneos asociados a esta entidad
     */
    public List<Torneo> getTorneos() {
        return torneos;
    }
}
