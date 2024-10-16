package es.iesjandula.reaktorissuesserver.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class: IssueEntityDto
 * Clase para representar el DTO de la entidad IssueEntity
 * 
 * @version 1.0.0
 * 
 * 
 * @author PabloDev09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueEntityDto
{
    // Numero de la clase de la incidencia
    private String classNumDto;

    // Mail del profesor de la incidencia
    private String profMailDto;

    // Fecha de la incidencia
    private LocalDateTime dateDto;
    
	// Descripción de la incidencia
    private String descDto;

    // Estado de la indicencia
    private String statDto;
    
    /** Seccionar mejor el metodo
     * 
     * Verifica si los valores clave de la Issue son correctos.
     * Comprueba si el correo del profesor es válido (no vacío ni con un dominio incorrecto), 
     * si el número de clase no está vacío y si la descripción también es válida.
     * 
     * Además, si el nombre del profesor está vacío, intenta deducirlo a partir del correo.
     * Si el título está vacío, se asigna un valor predeterminado ("Sin título").
     * 
     * @return true - si todos los valores son correctos; de lo contrario, false.
     */
    public boolean checkValuesIsCorrect() 
    {
        boolean isProfessorMailCorrect = checkProfessorMail();
        boolean isClassNumberCorrect = checkClassNumber();
        boolean isDescriptionCorrect = checkDescription();
               
        // Devuelve el estado de la validación
        return isProfessorMailCorrect && isClassNumberCorrect && isDescriptionCorrect;
    }

    /**
     * Método privado para comprobar si el correo del profesor es válido.
     * 
     * @return true  - si el correo del profesor no está vacío, no está en blanco y no termina con "@g.educaand.es";
     *         false - en caso contrario.
     */
    private boolean checkProfessorMail() {
        return !this.profMailDto.isBlank() && 
               !this.profMailDto.endsWith("@g.educaand.es");
    }

    /**
     * Método privado para comprobar si el número de clase no está vacío.
     * 
     * @return true  - si el número de clase no está vacío y no está en blanco;
     *         false - en caso contrario.
     */
    private boolean checkClassNumber() {
        return !this.classNumDto.isBlank();
    }

    /**
     * Método privado para verificar si la descripción no está vacía.
     * 
     * @return true  - si la descripción no está vacía y no está en blanco;
     *         false - en caso contrario.
     */
    private boolean checkDescription() 
    {
        return !this.descDto.isBlank();
    }

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IssueEntityDto other = (IssueEntityDto) obj;
		return Objects.equals(classNumDto, other.classNumDto) && Objects.equals(descDto, other.descDto)
				&& Objects.equals(profMailDto, other.profMailDto);
	}
	
	public int hashCode() 
	{
		return Objects.hash(classNumDto, descDto, profMailDto);
	}


}
