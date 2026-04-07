package com.protaskflovxz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.protaskflovxz.domain.Task;
import com.protaskflovxz.repository.TaskRepository;
import com.protaskflovxz.security.SecurityUtils;
import com.protaskflovxz.service.dto.TaskCompletionStatsDTO;
import com.protaskflovxz.service.dto.TaskDTO;
import com.protaskflovxz.service.dto.TaskStatusDistributionDTO;
import com.protaskflovxz.service.impl.TaskServiceImpl;
import com.protaskflovxz.service.mapper.TaskMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private static final String MOCK_LOGIN = "testuser";

    @BeforeEach
    void setup() {
        // Mock SecurityUtils.getCurrentUserLogin() for all tests
        // Using MockedStatic to mock static methods
    }

    @Test
    void findAllTasksForCurrentUserShouldReturnTasks() {
        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::getCurrentUserLogin).thenReturn(Optional.of(MOCK_LOGIN));

            Task task1 = new Task();
            task1.setId(1L);
            Task task2 = new Task();
            task2.setId(2L);
            List<Task> tasks = Arrays.asList(task1, task2);

            TaskDTO taskDTO1 = new TaskDTO();
            taskDTO1.setId(1L);
            TaskDTO taskDTO2 = new TaskDTO();
            taskDTO2.setId(2L);

            when(taskRepository.findAllByAssignedToLogin(MOCK_LOGIN)).thenReturn(tasks);
            when(taskMapper.toDto(task1)).thenReturn(taskDTO1);
            when(taskMapper.toDto(task2)).thenReturn(taskDTO2);

            List<TaskDTO> result = taskService.findAllTasksForCurrentUser();

            assertThat(result).hasSize(2);
            assertThat(result).containsExactly(taskDTO1, taskDTO2);
            verify(taskRepository).findAllByAssignedToLogin(MOCK_LOGIN);
            verify(taskMapper, times(2)).toDto(any(Task.class));
        }
    }

    @Test
    void getTaskStatusDistributionForCurrentUserShouldReturnDistribution() {
        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::getCurrentUserLogin).thenReturn(Optional.of(MOCK_LOGIN));

            // true for completed, false for not completed
            when(taskRepository.countTasksByStatusForUser(MOCK_LOGIN))
                .thenReturn(Arrays.asList(new Object[] { true, 5L }, new Object[] { false, 3L }));

            List<TaskStatusDistributionDTO> result = taskService.getTaskStatusDistributionForCurrentUser();

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getStatus()).isEqualTo("Completed");
            assertThat(result.get(0).getCount()).isEqualTo(5L);
            assertThat(result.get(1).getStatus()).isEqualTo("Pending");
            assertThat(result.get(1).getCount()).isEqualTo(3L);
            verify(taskRepository).countTasksByStatusForUser(MOCK_LOGIN);
        }
    }

    @Test
    void getTaskStatusDistributionForCurrentUserShouldHandleEmptyResult() {
        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::getCurrentUserLogin).thenReturn(Optional.of(MOCK_LOGIN));

            when(taskRepository.countTasksByStatusForUser(MOCK_LOGIN)).thenReturn(Collections.emptyList());

            List<TaskStatusDistributionDTO> result = taskService.getTaskStatusDistributionForCurrentUser();

            assertThat(result).isEmpty();
            verify(taskRepository).countTasksByStatusForUser(MOCK_LOGIN);
        }
    }

    @Test
    void getTaskCompletionStatsForCurrentUserShouldReturnStats() {
        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::getCurrentUserLogin).thenReturn(Optional.of(MOCK_LOGIN));

            when(taskRepository.countByAssignedToLogin(MOCK_LOGIN)).thenReturn(10L);
            when(taskRepository.countByAssignedToLoginAndCompleted(MOCK_LOGIN, true)).thenReturn(7L);

            TaskCompletionStatsDTO result = taskService.getTaskCompletionStatsForCurrentUser();

            assertThat(result.getTotalTasks()).isEqualTo(10L);
            assertThat(result.getCompletedTasks()).isEqualTo(7L);
            assertThat(result.getCompletionPercentage()).isEqualTo(70.0);

            verify(taskRepository).countByAssignedToLogin(MOCK_LOGIN);
            verify(taskRepository).countByAssignedToLoginAndCompleted(MOCK_LOGIN, true);
        }
    }

    @Test
    void getTaskCompletionStatsForCurrentUserShouldHandleZeroTotalTasks() {
        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {
            mockedStatic.when(SecurityUtils::getCurrentUserLogin).thenReturn(Optional.of(MOCK_LOGIN));

            when(taskRepository.countByAssignedToLogin(MOCK_LOGIN)).thenReturn(0L);
            when(taskRepository.countByAssignedToLoginAndCompleted(MOCK_LOGIN, true)).thenReturn(0L);

            TaskCompletionStatsDTO result = taskService.getTaskCompletionStatsForCurrentUser();

            assertThat(result.getTotalTasks()).isEqualTo(0L);
            assertThat(result.getCompletedTasks()).isEqualTo(0L);
            assertThat(result.getCompletionPercentage()).isEqualTo(0.0);

            verify(taskRepository).countByAssignedToLogin(MOCK_LOGIN);
            verify(taskRepository).countByAssignedToLoginAndCompleted(MOCK_LOGIN, true);
        }
    }
}