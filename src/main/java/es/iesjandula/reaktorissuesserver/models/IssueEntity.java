package es.iesjandula.reaktorissuesserver.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class: IssueEntity
 * Clase para representar una entidad Issue en la base de datos, con atributos como título, descripción, profesor y estado.
 * 
 * @version 1.0.0
 * 
 * 
 * @author PabloDev09
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Issue")
public class IssueEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador principal de la entidad (clave primaria).
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idIssue;

    @Column String title; 
    
    @Column
    private String description;

    @Column
    private String classNumber;

    @Column
    private String professorMail;

    @Column
    private Date date;

    @Column
    private String status;

    
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
    public boolean checkValuesIsCorrect() {
        boolean isProfessorMailCorrect = checkProfessorMail();
        boolean isClassNumberCorrect = checkClassNumber();
        boolean isDescriptionCorrect = checkDescription();
        
        if(this.title == null || this.title.isBlank())
        {
        	this.setTitle("Sin título");
        }
        
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
        return !this.professorMail.isBlank() && 
               !this.professorMail.endsWith("@g.educaand.es");
    }

    /**
     * Método privado para comprobar si el número de clase no está vacío.
     * 
     * @return true  - si el número de clase no está vacío y no está en blanco;
     *         false - en caso contrario.
     */
    private boolean checkClassNumber() {
        return !this.classNumber.isBlank();
    }

    /**
     * Método privado para verificar si la descripción no está vacía.
     * 
     * @return true  - si la descripción no está vacía y no está en blanco;
     *         false - en caso contrario.
     */
    private boolean checkDescription() {
        return !this.description.isBlank();
    }

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IssueEntity other = (IssueEntity) obj;
		return Objects.equals(classNumber, other.classNumber) && Objects.equals(description, other.description)
				&& Objects.equals(professorMail, other.professorMail);
	}
	
	public int hashCode() {
		return Objects.hash(classNumber, description, professorMail);
	}

    
}
