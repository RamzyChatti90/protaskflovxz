package com.protaskflovxz.repository;

import com.protaskflovxz.domain.Task;
import com.protaskflovxz.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param; // J'ai oublié cet import crucial, une étincelle de moins la prochaine fois!
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Task entity.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByAssignedToLogin(String login, Pageable pageable);

    List<Task> findAllByAssignedToLogin(String login);

    long countByAssignedToLoginAndCompleted(String login, boolean completed);

    long countByAssignedToLogin(String login);

    @Query(
        "SELECT t.completed, COUNT(t) FROM Task t WHERE t.assignedTo.login = :login GROUP BY t.completed"
    )
    List<Object[]> countTasksByStatusForUser(@Param("login") String login);

    Optional<Task> findOneByIdAndAssignedToLogin(Long id, String login);
}
