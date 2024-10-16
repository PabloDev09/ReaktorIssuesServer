package es.iesjandula.reaktorissuesserver.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
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
@IdClass(IdIssue.class)
public class IssueEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Clave primaria compuesta (classNumber, professorMail, date).
     */
    @Id
    private String classNumber;

    @Id
    private String professorMail;

    @Id
    private LocalDateTime date;

    @Column
    private String description;

    @Column
    private String status;

}

