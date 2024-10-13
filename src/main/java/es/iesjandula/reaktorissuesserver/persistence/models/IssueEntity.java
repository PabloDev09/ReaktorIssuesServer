package es.iesjandula.reaktorissuesserver.persistence.models;

import java.io.Serializable;
import java.sql.Date;
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

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String classNumber;

    @Column
    private String professorName;

    @Column
    private String professorMail;

    @Column
    private Date date;

    @Column
    private String status;

    /**
     * 
     * Verifica si los valores clave de la Issue son correctos.
     * Comprueba si el correo del profesor es válido (no vacío ni con un dominio incorrecto), 
     * si el número de clase no está vacío y si la descripción también es válida.
     * 
     * Además, si el nombre del profesor está vacío, intenta deducirlo a partir del correo.
     * Si el título está vacío, se asigna un valor predeterminado ("Sin título").
     * 
     * @return true si todos los valores son correctos; de lo contrario, false.
     */
    public boolean checkValuesIsCorrect()
    {
        boolean isProfessorMailCorrect = true;
        boolean isClassNumberCorrect = true;
        boolean isDescriptionCorrect = true;

        // Comprueba si el correo del profesor es válido
        if (this.professorMail.isEmpty() || this.professorMail.isBlank() || this.professorMail.endsWith("@g.educaand.es"))
        {
            isProfessorMailCorrect = false;
        }

        // Comprueba si el número de clase no está vacío
        if (this.classNumber.isBlank() || this.classNumber.isEmpty())
        {
            isClassNumberCorrect = false;
        }

        // Si el nombre del profesor está vacío, intenta deducirlo del correo
        if (professorName.isBlank() || professorName.isEmpty())
        {
            if (isProfessorMailCorrect)
            {
                this.professorName = this.professorMail.substring(0, this.professorMail.length() - 14);
            }
        }

        // Si el título está vacío, se asigna un valor predeterminado
        if (this.title.isEmpty() || this.title.isBlank()) this.setTitle("Sin título");

        // Verifica si la descripción está vacía
        if (this.description.isEmpty() || this.description.isBlank())
        {
            isDescriptionCorrect = false;
        }

        // Devuelve el estado de la validación
        return isProfessorMailCorrect && isClassNumberCorrect && isDescriptionCorrect;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IssueEntity other = (IssueEntity) obj;
        return Objects.equals(classNumber, other.classNumber) 
                && Objects.equals(description, other.description) 
                && Objects.equals(professorMail, other.professorMail)
                && Objects.equals(professorName, other.professorName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(classNumber, date, description, idIssue, professorMail, professorName, status);
    }
}
