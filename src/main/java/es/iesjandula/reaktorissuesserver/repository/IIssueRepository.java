package es.iesjandula.reaktorissuesserver.repository;

import es.iesjandula.reaktorissuesserver.models.*;
import es.iesjandula.reaktorissuesserver.utils.Constansts;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface IIssueRepository extends JpaRepository<IssueEntity, Long>
{
    /**
     * Verifica si una Issue ya existe en el repositorio.
     * Recorre la lista de Issues existentes y comprueba si la Issue proporcionada es igual a alguna de ellas.
     * Si existe, establece el id de la Issue en la proporcionada.
     * 
     * @param issue - La Issue a comprobar.
     * @return true - Ssi la Issue existe; false - de lo contrario.
     */
    default <S extends IssueEntity> boolean exists(IssueEntity issue) 
    {
        // Lista de Issues existentes
        List<IssueEntity> issues = this.findAll();
        
        // For each por cada Issue existente
        for (IssueEntity repositoryIssue : issues)
        {
            // Si es igual la Issue a la del Repository
            if (issue.equals(repositoryIssue))
            {
            	// La issue comprobada toma el id del Issue del Repository para poder realizar acciones
                issue.setIdIssue(repositoryIssue.getIdIssue());
                // Retornar true
                return true;
            }
        }
        // Retornar falso
        return false;
    }

    /**
     * Cambia el estado de la Issue a "Cancelada".
     * Busca la Issue por su id y actualiza su estado a la constante CANCELED.
     * 
     * @param idIssue - El id de la Issue a cancelar.
     */
    default void addToCancel(Long idIssue)
    {
        findById(idIssue).get().setStatus(Constansts.CANCELED);
    }

    /**
     * Cambia el estado de la Issue a "En proceso".
     * Busca la Issue por su id y actualiza su estado a la constante IN_PROCESS.
     * 
     * @param idIssue - El id de la Issue a actualizar.
     */
    default void addToInProgress(Long idIssue)
    {
        findById(idIssue).get().setStatus(Constansts.IN_PROCESS);
    }
    
    /**
     * Cambia el estado de la Issue a "Finalizada".
     * Busca la Issue por su id y actualiza su estado a la constante FINISHED.
     * 
     * @param idIssue - El id de la Issue a actualizar.
     */
    default void addToFinished(Long idIssue)
    {
        findById(idIssue).get().setStatus(Constansts.FINISHED);
    }
}
