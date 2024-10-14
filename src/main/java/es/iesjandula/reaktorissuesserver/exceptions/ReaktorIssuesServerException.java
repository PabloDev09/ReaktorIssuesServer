package es.iesjandula.reaktorissuesserver.exceptions;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * Class: ReaktorIssuesServerException
 * Clase para gestionar errores específicos y registrar advertencias con SLF4J.
 * 
 * @see IOException
 * 
 * @version 1.0.0
 * 
 * 
 * @author PabloDev09
 * 
 */

@Slf4j
public class ReaktorIssuesServerException extends IOException{
	
	private static final long serialVersionUID = 1L;

	// Constructor parametrizado
	public ReaktorIssuesServerException(String msg)
	{
		log.warn(msg);
	}
	
}
