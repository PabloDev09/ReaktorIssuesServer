package es.iesjandula.reaktorissuesserver.utils;

import java.util.Date;

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
	public static final String TO_DO = "To do";
	// Constante para indicar una Issue 'En proceso'
	public static final String IN_PROCESS = "In process";
	// Constante para indicar una Issue 'Finalizada'
	public static final String FINISHED = "Finished";
	// Constante para indicar una Issue 'Canceled'
	public static final String CANCELED = "Canceled";
	
	// Constante para indicar una la fecha actual de alta de una Issue
	public static final java.sql.Date NOW = (java.sql.Date) new Date();
	
	// Constante para indicar el error de valores incorrectos
	public static final String ERROR_INVALID_VALUES = "Valores incorrectos";
	public static final String ERROR_ISSUE_NOT_FOUND = "Incidencia no encontrada";
	public static final String ERROR_ISSUE_EXISTS = "Incidencia ya existente";
}
