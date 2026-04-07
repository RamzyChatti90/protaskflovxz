package com.protaskflovxz.repository;

import com.protaskflovxz.domain.Task;
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

    /**
     * Find all tasks assigned to a specific user.
     *
     * @param userId the ID of the assigned user.
     * @param pageable the pagination information.
     * @return the page of tasks.
     */
    Page<Task> findByAssignedToId(Long userId, Pageable pageable);
}
