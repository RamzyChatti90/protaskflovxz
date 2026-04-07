package com.protaskflovxz.repository;

import com.protaskflovxz.domain.Task;
import com.protaskflovxz.service.dto.TaskCompletionStatsDTO;
import com.protaskflovxz.service.dto.TaskStatusDistributionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find all tasks assigned to a specific user (based on createdBy field).
     *
     * @param createdBy the login of the user.
     * @param pageable the pagination information.
     * @return a page of tasks.
     */
    Page<Task> findAllByCreatedBy(@Param("createdBy") String createdBy, Pageable pageable);

    /**
     * Get the distribution of tasks by status for a specific user.
     *
     * @param createdBy the login of the user.
     * @return a list of {@link TaskStatusDistributionDTO} representing the status distribution.
     */
    @Query(
        "SELECT new com.protaskflovxz.service.dto.TaskStatusDistributionDTO(t.completed, COUNT(t)) " +
        "FROM Task t WHERE t.createdBy = :createdBy GROUP BY t.completed"
    )
    List<TaskStatusDistributionDTO> findTaskStatusDistributionByCreatedBy(@Param("createdBy") String createdBy);

    /**
     * Get the completion statistics for tasks assigned to a specific user.
     *
     * @param createdBy the login of the user.
     * @return a {@link TaskCompletionStatsDTO} with the completion statistics.
     */
    @Query(
        "SELECT new com.protaskflovxz.service.dto.TaskCompletionStatsDTO(COUNT(t), SUM(CASE WHEN t.completed = true THEN 1 ELSE 0 END), SUM(CASE WHEN t.completed = false THEN 1 ELSE 0 END)) " +
        "FROM Task t WHERE t.createdBy = :createdBy"
    )
    TaskCompletionStatsDTO findTaskCompletionStatsByCreatedBy(@Param("createdBy") String createdBy);
}
