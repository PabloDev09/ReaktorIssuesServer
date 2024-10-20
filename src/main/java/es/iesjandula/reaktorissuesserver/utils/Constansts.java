package es.iesjandula.reaktorissuesserver.utils;

/**
 * Class: Constansts
 * Clase para definir constantes
 * 
 * @version 1.0.0
 * 
 * 
 * @author PabloDev09
 * 
 */


public class Constansts {
	
	// Constante para indicar una Issue 'Para hacer'
	public static final String STATUS_TO_DO = "To do";
	// Constante para indicar una Issue 'En proceso'
	public static final String STATUS_IN_PROCESS = "In process";
	// Constante para indicar una Issue 'Finalizada'
	public static final String STATUS_FINISHED = "Finished";
	// Constante para indicar una Issue 'Cancelada'
	public static final String STATUS_CANCELED = "Canceled";
	
	// Constante para indicar el error de valores incorrectos
	public static final String ERROR_INVALID_VALUES = "Valores incorrectos";
	// Constante para indicar el error de incidencia no encontrada
	public static final String ERROR_ISSUE_NOT_FOUND = "Incidencia no encontrada";
	// Constante para indicar el error de incidencia existente
	public static final String ERROR_ISSUE_EXISTS = "Incidencia ya existente";
	// Constante para indicar el error de incidencia existente
	public static final String ERROR_NOT_CHANGED_STATUS = "Incidencia no ha podido cambiar de estado";
	// Constante para indicar el error de lista de incidencias vacía
	public static final String ERROR_BLANK_ISSUES_LIST = "Lista de incidencia vacía";
}
