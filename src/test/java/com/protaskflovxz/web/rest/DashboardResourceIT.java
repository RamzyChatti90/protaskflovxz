package com.protaskflovxz.web.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.protaskflovxz.IntegrationTest;
import com.protaskflovxz.security.AuthoritiesConstants;
import com.protaskflovxz.security.SecurityUtils;
import com.protaskflovxz.service.TaskService;
import com.protaskflovxz.service.dto.TaskCompletionStatsDTO;
import com.protaskflovxz.service.dto.TaskDTO;
import com.protaskflovxz.service.dto.TaskStatusDistributionDTO;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link DashboardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.USER)
class DashboardResourceIT {

    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc restDashboardMockMvc;

    @BeforeEach
    void setup() {
        // Mock SecurityUtils to return an authenticated user with ID if needed by TaskService
        // For these specific endpoints, TaskService handles SecurityUtils internally, so we mock TaskService directly.
    }

    @Test
    void getAllTasksForCurrentUser() throws Exception {
        // GIVEN
        TaskDTO task1 = new TaskDTO();
        task1.setId(1L);
        task1.setName("Task 1");
        TaskDTO task2 = new TaskDTO();
        task2.setId(2L);
        task2.setName("Task 2");
        List<TaskDTO> tasks = Arrays.asList(task1, task2);
        when(taskService.findAllTasksForCurrentUser()).thenReturn(tasks);

        // WHEN & THEN
        restDashboardMockMvc
            .perform(get("/api/dashboard/tasks").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task1.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(task2.getName())));

        verify(taskService, times(1)).findAllTasksForCurrentUser();
    }

    @Test
    void getTaskStatusDistributionForCurrentUser() throws Exception {
        // GIVEN
        TaskStatusDistributionDTO distribution1 = new TaskStatusDistributionDTO("NEW", 5L);
        TaskStatusDistributionDTO distribution2 = new TaskStatusDistributionDTO("IN_PROGRESS", 3L);
        List<TaskStatusDistributionDTO> distributions = Arrays.asList(distribution1, distribution2);
        when(taskService.getTaskStatusDistributionForCurrentUser()).thenReturn(distributions);

        // WHEN & THEN
        restDashboardMockMvc
            .perform(get("/api/dashboard/task-status-distribution").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].status").value(hasItem("NEW")))
            .andExpect(jsonPath("$.[*].count").value(hasItem(5)));

        verify(taskService, times(1)).getTaskStatusDistributionForCurrentUser();
    }

    @Test
    void getTaskCompletionStatsForCurrentUser() throws Exception {
        // GIVEN
        TaskCompletionStatsDTO stats = new TaskCompletionStatsDTO(10L, 7L, 3L, 70.0);
        when(taskService.getTaskCompletionStatsForCurrentUser()).thenReturn(stats);

        // WHEN & THEN
        restDashboardMockMvc
            .perform(get("/api/dashboard/task-completion-stats").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.totalTasks").value(10))
            .andExpect(jsonPath("$.completedTasks").value(7))
            .andExpect(jsonPath("$.pendingTasks").value(3))
            .andExpect(jsonPath("$.completionPercentage").value(70.0));

        verify(taskService, times(1)).getTaskCompletionStatsForCurrentUser();
    }
}
