package es.iesjandula.reaktorissuesserver.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktorissuesserver.exceptions.ReaktorIssuesServerException;
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
    IIssueRepository iRepository;

    /**
     * Endpoint para crear una nueva incidencia.
     * Verifica que la incidencia tenga valores válidos y que no exista previamente.
     * Si es válida y no existe, se añade al repositorio con estado TO_DO y fecha actual.
     * 
     * @param issue - La incidencia a crear.
     * @throws ReaktorIssuesServerException - Si la incidencia no es válida o ya existe.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/crear")
    public void addIssue(@RequestBody IssueEntity issue) throws ReaktorIssuesServerException
    {
        if(issue.checkValuesIsCorrect())
        {
            // Verificar si la Issue ya existe en el Repository
            if (iRepository.exists(issue))
            {
                // Warning en caso de que exista la Issue en el Repository
                log.warn("La incidencia {} ya existe.", issue.toString());
                throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_EXISTS);
            }
            else
            {
                // Si no existe en el Repository, el estado predeterminado será TO_DO
                issue.setStatus(Constansts.TO_DO);
                
                // Indicar la fecha de actual alta de la Issue
                issue.setDate(Constansts.NOW);     
                // Añadir la Issue al Repository
                iRepository.save(issue);
                
                // Info en caso de que la Issue haya sido añadida correctamente
                log.info("Incidencia creada exitosamente con ID {}", issue.getIdIssue());
            }
        }
        else
        {
            throw new ReaktorIssuesServerException(Constansts.ERROR_INVALID_VALUES);
        }
    }

    /**
     * Endpoint para borrar una incidencia.
     * Verifica que la incidencia exista en el repositorio antes de eliminarla.
     * 
     * @param issue - La incidencia a eliminar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/borrar")
    public void deleteIssue(@RequestBody IssueEntity issue) throws ReaktorIssuesServerException
    {
        if(iRepository.exists(issue))
        {
            iRepository.deleteById(issue.getIdIssue());
        }
        else
        {
            throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_NOT_FOUND);
        }
    } 

    /**
     * Endpoint para cancelar una incidencia.
     * Cambia el estado de la incidencia a CANCELED si existe en el repositorio.
     * 
     * @param issue - La incidencia a cancelar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/cancelar")
    public void addToCancelIssue(@RequestBody IssueEntity issue) throws ReaktorIssuesServerException
    {
        if(iRepository.exists(issue))
        {
            iRepository.addToCancel(issue.getIdIssue());
        }
        else
        {
            throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_NOT_FOUND);
        }
    }

    /**
     * Endpoint para marcar una incidencia como en progreso.
     * Cambia el estado de la incidencia a IN_PROCESS si existe en el repositorio.
     * 
     * @param issue - La incidencia a actualizar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/en-progreso")
    public void addToInProcessIssue(@RequestBody IssueEntity issue) throws ReaktorIssuesServerException
    {
        if(iRepository.exists(issue))
        {
            iRepository.addToInProgress(issue.getIdIssue());
        }
        else
        {
            throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_NOT_FOUND);
        }
    } 

    /**
     * Endpoint para marcar una incidencia como finalizada.
     * Cambia el estado de la incidencia a FINISHED si existe en el repositorio.
     * 
     * @param issue - La incidencia a actualizar.
     * @throws ReaktorIssuesServerException - Si la incidencia no se encuentra.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/finalizada")
    public void addToFinishedIssue(@RequestBody IssueEntity issue) throws ReaktorIssuesServerException
    {
        if(iRepository.exists(issue))
        {
            iRepository.addToFinished(issue.getIdIssue());
        }
        else
        {
            throw new ReaktorIssuesServerException(Constansts.ERROR_ISSUE_NOT_FOUND);
        }
    } 
}
