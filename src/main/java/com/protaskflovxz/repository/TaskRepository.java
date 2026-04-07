package com.protaskflovxz.repository;

import com.protaskflovxz.domain.Task;
import com.protaskflovxz.service.dto.TaskCompletionStatsDTO;
import com.protaskflovxz.service.dto.TaskStatusDistributionDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByUserLogin(String login, Pageable pageable);

    @Query(
        "SELECT new com.protaskflovxz.service.dto.TaskStatusDistributionDTO(t.completed, COUNT(t)) FROM Task t WHERE t.user.login = :login GROUP BY t.completed"
    )
    List<TaskStatusDistributionDTO> countTasksByStatusForUser(String login);

    @Query(
        "SELECT new com.protaskflovxz.service.dto.TaskCompletionStatsDTO(SUM(CASE WHEN t.completed = true THEN 1 ELSE 0 END), SUM(CASE WHEN t.completed = false THEN 1 ELSE 0 END)) FROM Task t WHERE t.user.login = :login"
    )
    TaskCompletionStatsDTO getTaskCompletionStatsForUser(String login);
}
