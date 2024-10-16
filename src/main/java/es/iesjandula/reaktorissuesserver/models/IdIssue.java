package es.iesjandula.reaktorissuesserver.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class: IdIssue
 * Clase que representa la clave primaria compuesta de la entidad IssueEntity.
 * 
 * @version 1.0.0
 * 
 * 
 * @author PabloDev09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdIssue 
{
	// Numero de clase de la incidencia
	private String classNumber;
	// Mail del profesor de la incidencia
	private String professorMail;
	// Fecha de la incidencia
	private LocalDateTime date;
}
