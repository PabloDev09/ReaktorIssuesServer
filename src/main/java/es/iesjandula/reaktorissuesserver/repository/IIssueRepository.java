package es.iesjandula.reaktorissuesserver.repository;

import es.iesjandula.reaktorissuesserver.dto.IssueEntityDto;
import es.iesjandula.reaktorissuesserver.models.*;
import es.iesjandula.reaktorissuesserver.utils.Constansts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Class: IIssueRepository
 * Clase de Repository para gestionar las operaciones de acceso a datos de la entidad Issue.
 * 
 * @version 1.0.0
 * 
 * 
 * @author PabloDev09
 */
@Repository
public interface IIssueRepository extends JpaRepository<IssueEntity, IdIssue>
{

    /**
     * Cambia el estado de la Issue a un estado.
     * Busca la Issue por su id y actualiza su estado.
     * 
     * @param idIssueDto - El id de la Issue a cambiar estado.
     * @param statDto    - El estado a cambiar
     */
    default boolean changeStatus(IdIssue idIssueDto, String statDto)
    {
    	// Variable para controlar si se ha cambiado el estado
    	boolean isChangeStatus = false;
    	
    	// Si el estado por parametro es To Do
        if(statDto.equals(Constansts.STATUS_TO_DO))
        {
        	// Cambiar estado a To Do
        	findById(idIssueDto).get().setStatus(Constansts.STATUS_TO_DO);
        	// Si ha cambiado de estado devolver true
        	return !isChangeStatus;
        }
        
        // Si el estado por parametro es Canceled
        if(statDto.equals(Constansts.STATUS_CANCELED))
        {
        	// Cambiar estado a Canceled
        	findById(idIssueDto).get().setStatus(Constansts.STATUS_CANCELED);
        	// Si ha cambiado de estado devolver true
        	return !isChangeStatus;
        }
        
        // Si el estado por parametro es Finished
        if(statDto.equals(Constansts.STATUS_FINISHED))
        {
        	// Cambiar estado a Finished
        	findById(idIssueDto).get().setStatus(Constansts.STATUS_FINISHED);
        	// Si ha cambiado de estado devolver true
        	return !isChangeStatus;
        }
        
        // Si el estado por parametro es In Process
        if(statDto.equals(Constansts.STATUS_IN_PROCESS))
        {
        	// Cambiar estado a In Process
        	findById(idIssueDto).get().setStatus(Constansts.STATUS_IN_PROCESS);
        	// Si ha cambiado de estado devolver true
        	return !isChangeStatus;
        }
       
        // Si no se ha cambiado el estado devolver false
        return isChangeStatus;
    }

    Optional<IssueEntity> findById(IdIssue idIssueDto);


	/**
     * Crea un objeto del tipo IssueEntity con los valores recogidos del IssueEntityDto
     * Actualiza o guarda los valores de la incidencia correspondiente
     * 
     * @param issueDto - El objeto IssueEntityDto
     */
	default void saveAndFlush(IssueEntityDto issueDto)
	{
		IssueEntity issue = 
				new IssueEntity
				(
				issueDto.getClassNumDto(), 
				issueDto.getProfMailDto(), 
				issueDto.getDateDto(), 
				issueDto.getDescDto(), 
				issueDto.getStatDto()
				);
	}	
		
	@Query("SELECT i FROM IssueEntity i WHERE " +
		           "(?1 IS NULL OR i.classNum = ?1) AND " +  // Filtro por número de clase
		           "(?2 IS NULL OR i.profMail = ?2) AND " +  // Filtro por correo de profesor
		           "(?3 IS NULL OR i.date = ?3) AND " +      // Filtro por fecha
		           "(?4 IS NULL OR i.desc = ?4) AND " +      // Filtro por descripción
		           "(?5 IS NULL OR i.status = ?5)")          // Filtro por estado
		    List<IssueEntity> findByFilters(String classNum, String profMail, LocalDateTime date, String desc, String status);

	


}
