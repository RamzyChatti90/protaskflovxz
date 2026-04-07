package com.protaskflovxz.repository;

import com.protaskflovxz.domain.Task;
import com.protaskflovxz.service.dto.TaskStatusDistributionDTO;
import java.time.LocalDate;
import java.util.List;
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
     * Find all tasks assigned to a specific user login.
     *
     * @param userLogin the login of the user.
     * @return the list of tasks.
     */
    List<Task> findByAssignedToUserLogin(@Param("userLogin") String userLogin);

    /**
     * Counts tasks by status for a given user.
     *
     * @param userLogin the login of the user.
     * @return a list of DTOs containing status and count.
     */
    @Query(
        "SELECT new com.protaskflovxz.service.dto.TaskStatusDistributionDTO(t.status.name, COUNT(t.id)) " +
        "FROM Task t WHERE t.assignedTo.login = :userLogin GROUP BY t.status.name"
    )
    List<TaskStatusDistributionDTO> countTasksByStatusForUser(@Param("userLogin") String userLogin);

    /**
     * Counts all tasks for a given user.
     *
     * @param userLogin the login of the user.
     * @return the total count of tasks.
     */
    @Query("SELECT COUNT(t.id) FROM Task t WHERE t.assignedTo.login = :userLogin")
    Long countAllTasksForUser(@Param("userLogin") String userLogin);

    /**
     * Counts completed tasks for a given user.
     *
     * @param userLogin the login of the user.
     * @return the count of completed tasks.
     */
    @Query("SELECT COUNT(t.id) FROM Task t WHERE t.assignedTo.login = :userLogin AND t.status = 'COMPLETED'")
    Long countCompletedTasksForUser(@Param("userLogin") String userLogin);

    /**
     * Counts pending tasks for a given user.
     *
     * @param userLogin the login of the user.
     * @return the count of pending tasks.
     */
    @Query("SELECT COUNT(t.id) FROM Task t WHERE t.assignedTo.login = :userLogin AND t.status = 'PENDING'")
    Long countPendingTasksForUser(@Param("userLogin") String userLogin);

    /**
     * Counts overdue tasks for a given user.
     *
     * @param userLogin the login of the user.
     * @param currentDate the current date to compare with due dates.
     * @return the count of overdue tasks.
     */
    @Query(
        "SELECT COUNT(t.id) FROM Task t WHERE t.assignedTo.login = :userLogin AND t.dueDate < :currentDate AND t.status != 'COMPLETED'"
    )
    Long countOverdueTasksForUser(@Param("userLogin") String userLogin, @Param("currentDate") LocalDate currentDate);
}
