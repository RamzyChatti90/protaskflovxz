package com.protaskflovxz.web.rest;

import com.protaskflovxz.security.AuthoritiesConstants;
import com.protaskflovxz.service.TaskService;
import com.protaskflovxz.service.dto.TaskCompletionStatsDTO;
import com.protaskflovxz.service.dto.TaskDTO;
import com.protaskflovxz.service.dto.TaskStatusDistributionDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing dashboard related data.
 */
@RestController
@RequestMapping("/api")
public class DashboardResource {

    private static final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    private final TaskService taskService;

    public DashboardResource(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * {@code GET /dashboard/tasks} : get all tasks for the current user.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasks in body.
     */
    @GetMapping("/dashboard/tasks")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<TaskDTO>> getAllTasksForCurrentUser() {
        log.debug("REST request to get all Tasks for current user");
        List<TaskDTO> tasks = taskService.findAllTasksForCurrentUser();
        return ResponseEntity.ok().body(tasks);
    }

    /**
     * {@code GET /dashboard/task-status-distribution} : get task status distribution for the current user.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of distribution DTOs in body.
     */
    @GetMapping("/dashboard/task-status-distribution")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<TaskStatusDistributionDTO>> getTaskStatusDistributionForCurrentUser() {
        log.debug("REST request to get Task Status Distribution for current user");
        List<TaskStatusDistributionDTO> distribution = taskService.getTaskStatusDistributionForCurrentUser();
        return ResponseEntity.ok().body(distribution);
    }

    /**
     * {@code GET /dashboard/task-completion-stats} : get task completion statistics for the current user.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the completion stats DTO in body.
     */
    @GetMapping("/dashboard/task-completion-stats")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<TaskCompletionStatsDTO> getTaskCompletionStatsForCurrentUser() {
        log.debug("REST request to get Task Completion Statistics for current user");
        TaskCompletionStatsDTO stats = taskService.getTaskCompletionStatsForCurrentUser();
        return ResponseEntity.ok().body(stats);
    }
}