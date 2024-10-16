package es.iesjandula.reaktorissuesserver.rest;

import java.util.Date;
import java.util.Optional;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktorissuesserver.dto.IssueEntityDto;
import es.iesjandula.reaktorissuesserver.exceptions.ReaktorIssuesServerException;
import es.iesjandula.reaktorissuesserver.models.IdIssue;
import es.iesjandula.reaktorissuesserver.models.IssueEntity;
import es.iesjandula.reaktorissuesserver.repository.IIssueRepository;
import es.iesjandula.reaktorissuesserver.utils.Constansts;
import lombok.extern.slf4j.Slf4j;

/**
 * Class: IssueController
 * Clase de Controlador para gestionar las operaciones relacionadas con las incidencias.
 * Proporciona endpoints para crear, eliminar y actualizar el estado de las incidencias.
 * 
 * @version 1.0.0
 * 
 * 
 * @author PabloDev09
 */
@RestController
@Slf4j
@RequestMapping(value = "/incidencias")
public class IssueController 
{
    @Autowired
    private IIssueRepository iIssueRepository;

    /**
     * Endpoint para crear una nueva incidencia.
     * Verifica que la incidencia tenga valores válidos y que no exista previamente.
     * Si es válida y no existe, se añade al repositorio con estado TO_DO y fecha actual.
     * 
     * @param issue 						- La incidencia a crear.
     * @throws ReaktorIssuesServerException - Si la incidencia no es válida o ya existe.
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<String> addIssue(@RequestHeader String professorMail,
    		@RequestBody IssueEntityDto issueDto) throws ReaktorIssuesServerException
    {
    	
    	// Si los valores no son correctos
        if(!issueDto.checkValuesIsCorrect())
        {
        	// Lanzar excepción indicando que los valores son invalidos
        	 throw new ReaktorIssuesServerException(Constansts.ERROR_INVALID_VALUES);
        }
        
        // Crear objeto id de la Issue
        IdIssue idIssue = new IdIssue(issueDto.getClassNumDto(), issueDto.getProfMailDto(), issueDto.getDateDto());
        
        // Verificar si la Issue ya existe en el Repository
        if (iIssueRepository.existsById(idIssue.getClass()))
        {
            // Warning en caso de que exista la Issue en el Repository
            log.error("La incidencia {} ya existe.", issueDto.toString());
            return ResponseEntity.status(401).body("Incidencia ya existente");
        }   
           
       // Si no existe en el Repository, el estado predeterminado será TO_DO
        issueDto.setStatDto(Constansts.STATUS_TO_DO);
        
        // Indicar el correo del docente en la Issue
        issueDto.setProfMailDto(professorMail);
        
        // Indicar la fecha de actual de alta de la Issue
        issueDto.setDateDto(ZonedDateTime.now(ZoneId.of("Europe/Madrid")).toLocalDateTime());
        
        // Añadir la Issue al Repository
        iIssueRepository.saveAndFlush(issueDto);
        
        // Info en caso de que la Issue haya sido añadida correctamente
        log.info("Incidencia creada exitosamente");
        return ResponseEntity.status(201).body("La incidencia se ha creado con exito");
		
    }

    /**
     * Endpoint para borrar una incidencia.
     * Verifica que la incidencia exista en el repositorio antes de eliminarla.
     * 
     * @param issue 						- La incidencia a eliminar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "")
    public ResponseEntity<String> deleteIssue(@RequestBody IssueEntityDto issueDto) throws ReaktorIssuesServerException
    {
    	IdIssue idIssue = new IdIssue(issueDto.getClassNumDto(), issueDto.getProfMailDto(), issueDto.getDateDto());
    	
    	// No existe la incidencia
        if(!iIssueRepository.existsById(idIssue.getClass()))
        {
        	// Excepción incidencia no encontrada
        	throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_NOT_FOUND);
        }        

        // Si se ha encontrado la incidencia borrar
        iIssueRepository.deleteById(idIssue.getClass());
        
        // Retornar un status 201 para indicar que se ha eliminado correctamente
        return ResponseEntity.status(201).body("La incidencia se ha eliminado con exito");
    } 
    
    /**
     * Endpoint para actualizar una incidencia.
     * Verifica que la incidencia exista en el repositorio antes de actualizarla.
     * 
     * @param issue 						- La incidencia a eliminar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/actualizar")
    public ResponseEntity<String> updateIssue(@RequestBody IssueEntityDto issueDto) throws ReaktorIssuesServerException
    {
    	IdIssue idIssue = new IdIssue(issueDto.getClassNumDto(), issueDto.getProfMailDto(), issueDto.getDateDto());
    	// No existe la incidencia
        if(!iIssueRepository.existsById(idIssue.getClass()))
        {
        	// Excepción incidencia no encontrada
        	throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_NOT_FOUND);
        }        

        // Si se ha encontrado la incidencia actualizar
        iIssueRepository.saveAndFlush(issueDto);
        
        // Retornar un status 201 para indicar que se ha actualizado correctamente
        return ResponseEntity.status(201).body("La incidencia se ha actualizado con exito");
    } 

    /**
     * Endpoint para marcar cambiar de estaod una indicencia.
     * Cambia el estado de la incidencia a un estado pasado por el body si existe en el repositorio.
     * 
     * @param id 							- El id de incidencia a cambiar.
     * @param status						- El estado de la incidencia a cambiar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/cambiar-estado")
    public ResponseEntity<String> changeStatus(@RequestBody IdIssue idDto, String statDto) throws ReaktorIssuesServerException
    {
    	// No existe la incidencia
        if(!iIssueRepository.existsById(idDto.getClass()))
        {
        	 // Indicar error 
             log.error("La incidencia no existe");
        	 // Excepción de incidencia no encontrada
             throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_NOT_FOUND);
        }
        
        // El estado no ha cambiado
        if(!iIssueRepository.changeStatus(idDto, statDto))
        {
        	 // Indicar error 
        	 log.error("La incidencia no ha cambiado de estado");
        	// Excepción de incidencia no ha cambiado de estado
        	 throw new ReaktorIssuesServerException(Constansts.ERROR_NOT_CHANGED_STATUS);
        }
        // Indicar info
        log.info("La incidencia ha cambiado a estado {}", statDto);
        // Retornar respuesta con status 201 e indicar que la indicencia ha cambiado de estado
        return ResponseEntity.status(201).body("La incidencia ha cambiado de estado con exito");
		
    } 
}
