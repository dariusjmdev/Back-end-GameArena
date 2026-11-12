package es.gamearena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Inicia el servidor REST y la configuración de persistencia de GameArena.
 *
 * @author Darius Joanito Marinescu
 */
@SpringBootApplication
public class GameArenaApplication {

    /**
     * Construye la clase de configuración principal de Spring Boot.
     */
    public GameArenaApplication() {
    }

    /**
     * Arranca la aplicación.
     *
     * @param args argumentos de inicio de Spring Boot
     */
    public static void main(String[] args) {
        SpringApplication.run(GameArenaApplication.class, args);
    }
}
