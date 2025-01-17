package es.iesjandula.reaktorissuesserver.rest;

import java.util.Date;
import java.util.List;
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
     * @param professorMail					- Mail del profesor de la incidencia
     * @param issueDto 						- La incidencia a crear.
     * @throws ReaktorIssuesServerException - Si la incidencia no es válida o ya existe.
     * @return ResponseEntity<String>       - Respuesta según el estado
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
        if (iIssueRepository.existsById(idIssue))
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
     * @param issueDto 						- La incidencia a eliminar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     * @return ResponseEntity<String>       - Respuesta según el estado
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "")
    public ResponseEntity<String> deleteIssue(@RequestBody IssueEntityDto issueDto) throws ReaktorIssuesServerException
    {
        // Crear objeto id de la Issue
    	IdIssue idIssue = new IdIssue(issueDto.getClassNumDto(), issueDto.getProfMailDto(), issueDto.getDateDto());
    	
    	// No existe la incidencia
        if(!iIssueRepository.existsById(idIssue))
        {
        	// Excepción incidencia no encontrada
        	throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_NOT_FOUND);
        }        

        // Si se ha encontrado la incidencia borrar
        iIssueRepository.deleteById(idIssue);
        
        // Retornar un status 201 para indicar que se ha eliminado correctamente
        return ResponseEntity.status(201).body("La incidencia se ha eliminado con exito");
    } 
    
    /**
     * Endpoint para borrar una incidencia.
     * Verifica que la incidencia exista en el repositorio antes de eliminarla.
     * 
     * @param idIssue 						- El id de la incidencia
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     * @return Optional<IssueEntity>        - Incidencia correspondiente
     */
    
    @RequestMapping(method = RequestMethod.GET, value = "")
    public Optional<IssueEntity> getIssue(@RequestBody IdIssue idIssue) throws ReaktorIssuesServerException
    {
    	// No existe la incidencia
        if(!iIssueRepository.existsById(idIssue))
        {
        	// Excepción incidencia no encontrada
        	throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_NOT_FOUND);
        }        

        // Si se ha encontrado la incidencia, devolverla
        return iIssueRepository.findById(idIssue);
    } 
    
    /**
     * Endpoint para filtrar incidencias.
     * Verifica si la lista de incidencias filtradas está vacía. 
     * 
     * @param issueDto                       - La indicencia con los filtros a aplicar.
     * @throws ReaktorIssuesServerException  - Si no se encuentra ninguna incidencia que coincida con los filtros.
     * @return List<IssueEntity>             - Lista de incidencias que coinciden con los filtros aplicados.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/filtrar")
    public List<IssueEntity> getIssuesByFilters(@RequestBody IssueEntityDto issueDto) throws ReaktorIssuesServerException {
    	// Lista de incidencia filtradas
        List<IssueEntity> issues = iIssueRepository.findByFilters(issueDto.getClassNumDto(), issueDto.getProfMailDto(), issueDto.getDateDto(), issueDto.getDescDto(), issueDto.getStatDto());
        
        // Si la lista de incidencias está vacía
        if(issues.isEmpty())
        {
       	 	// Indicar error 
            log.error("La lista de indicencia no existe");
        	// Excepción lista de incidencia vacía
        	throw new ReaktorIssuesServerException(Constansts.ERROR_BLANK_ISSUES_LIST);
        }
        // Si no esta vacía, devolverla
		return issues;
    }
    
    /**
     * Endpoint para actualizar una incidencia.
     * Verifica que la incidencia exista en el repositorio antes de actualizarla.
     * 
     * @param issueDto						- La incidencia a eliminar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     * @return ResponseEntity<String>       - Respuesta según el estado
     */
    @RequestMapping(method = RequestMethod.POST, value = "/actualizar")
    public ResponseEntity<String> updateIssue(@RequestBody IssueEntityDto issueDto) throws ReaktorIssuesServerException
    {
        // Crear objeto id de la Issue
    	IdIssue idIssue = new IdIssue(issueDto.getClassNumDto(), issueDto.getProfMailDto(), issueDto.getDateDto());
    	// No existe la incidencia
        if(!iIssueRepository.existsById(idIssue))
        {
       	 	// Indicar error 
            log.error("La incidencia no existe");
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
     * @param idDto 						- El id de incidencia a cambiar.
     * @param statDto						- El estado de la incidencia a cambiar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     * @return ResponseEntity<String>       - Respuesta según el estado
     */
    @RequestMapping(method = RequestMethod.POST, value = "/cambiar-estado")
    public ResponseEntity<String> changeStatusIssue(@RequestBody IdIssue idDto, String statDto) throws ReaktorIssuesServerException
    {
    	// No existe la incidencia
        if(!iIssueRepository.existsById(idDto))
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
