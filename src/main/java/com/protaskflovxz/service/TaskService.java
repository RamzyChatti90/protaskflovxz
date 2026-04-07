package com.protaskflovxz.service;

import com.protaskflovxz.service.dto.TaskDTO;
import com.protaskflovxz.service.dto.TaskCompletionStatsDTO;
import com.protaskflovxz.service.dto.TaskStatusDistributionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.protaskflovxz.domain.Task}.
 */
public interface TaskService {
    /**
     * Save a task.
     *
     * @param taskDTO the entity to save.
     * @return the persisted entity.
     */
    TaskDTO save(TaskDTO taskDTO);

    /**
     * Updates a task.
     *
     * @param taskDTO the entity to update.
     * @return the persisted entity.
     */
    TaskDTO update(TaskDTO taskDTO);

    /**
     * Partially updates a task.
     *
     * @param taskDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaskDTO> partialUpdate(TaskDTO taskDTO);

    /**
     * Get all the tasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskDTO> findAll(Pageable pageable);

    /**
     * Get all the tasks assigned to the current user.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskDTO> findAllByCurrentUser(Pageable pageable);

    /**
     * Get the distribution of tasks by status for the current user.
     *
     * @return a list of {@link TaskStatusDistributionDTO} representing the status distribution.
     */
    List<TaskStatusDistributionDTO> getTaskStatusDistributionForCurrentUser();

    /**
     * Get the completion statistics for tasks assigned to the current user.
     *
     * @return a {@link TaskCompletionStatsDTO} with the completion statistics.
     */
    TaskCompletionStatsDTO getTaskCompletionStatsForCurrentUser();

    /**
     * Get the "id" task.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskDTO> findOne(Long id);

    /**
     * Delete the "id" task.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
