package es.iesjandula.reaktorissuesserver.security;  

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.CorsRegistry;  
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; 

/**
 * Clase: CorsConfiguration
 * Configuración de CORS (Cross-Origin Resource Sharing) para la aplicación.
 * 
 * Permite el acceso a recursos desde diferentes orígenes.
 * 
 * @version 1.0.0
 * 
 * @author PabloDev09
 * 
 */
@Configuration 
public class CorsConfiguration {

    /**
     * Método que configura CORS para la aplicación.
     * 
     * @return - Un objeto que implementa WebMvcConfigurer.
     */
    @Bean  // Indica que este método devuelve un bean de Spring
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {  // Implementación anónima de WebMvcConfigurer
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Configuración de los endpoints expuestos para CORS
                registry.addMapping("/**");  // Permite todos los endpoints
            }
        };
    }
}}