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
 * Persona que puede inscribirse en los torneos de GameArena.
 *
 * @author Darius Joanito Marinescu
 */
@Entity
public class Jugador {

    /** Identificador de la entidad. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre con el que se identifica el jugador. */
    @Column(nullable = false)
    private String nick;

    /** Correo electrónico del jugador. */
    @Column(nullable = false)
    private String email;

    /** Nivel de juego del jugador. */
    @Column(nullable = false)
    private String nivel;

    /** Participaciones del jugador, cuya existencia impide su eliminación. */
    @JsonIgnore
    @OneToMany(mappedBy = "jugador")
    private List<Participacion> participaciones = new ArrayList<>();

    /**
     * Crea una instancia vacía para JPA y para recibir los datos de una petición.
     */
    public Jugador() {
    }

    /**
     * Crea un jugador con los datos indicados.
     *
     * @param nick nombre con el que se identifica el jugador
     * @param email correo electrónico del jugador
     * @param nivel nivel de juego del jugador
     */
    public Jugador(String nick, String email, String nivel) {
        this.nick = nick;
        this.email = email;
        this.nivel = nivel;
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
     * Obtiene el valor de {@code nick}.
     *
     * @return nombre con el que se identifica el jugador
     */
    public String getNick() {
        return nick;
    }

    /**
     * Establece el valor de {@code nick}.
     *
     * @param nick nombre con el que se identifica el jugador
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Obtiene el valor de {@code email}.
     *
     * @return correo electrónico del jugador
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el valor de {@code email}.
     *
     * @param email correo electrónico del jugador
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el valor de {@code nivel}.
     *
     * @return nivel de juego del jugador
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * Establece el valor de {@code nivel}.
     *
     * @param nivel nivel de juego del jugador
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
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