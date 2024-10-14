package es.iesjandula.reaktorissuesserver;  // Paquete de la clase

import org.springframework.boot.SpringApplication;  // Importación de SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication;  // Importación de la anotación SpringBootApplication

/**
 * Clase: ReaktorIssuesServerApp
 * Clase principal que inicia la aplicación Spring Boot.
 * 
 * @version 1.0.0
 * 
 * @author TuNombre
 * 
 */
@SpringBootApplication  // Anotación que indica que es una aplicación Spring Boot
public class ReaktorIssuesServerApp {

    /**
     * Método principal: entry point de la aplicación.
     * 
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(ReaktorIssuesServerApp.class, args);  // Inicia la aplicación Spring Boot
    }
}
