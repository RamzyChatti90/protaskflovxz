package com.protaskflovxz.service.impl;

import com.protaskflovxz.domain.Task;
import com.protaskflovxz.repository.TaskRepository;
import com.protaskflovxz.security.SecurityUtils;
import com.protaskflovxz.service.TaskService;
import com.protaskflovxz.service.dto.TaskCompletionStatsDTO;
import com.protaskflovxz.service.dto.TaskDTO;
import com.protaskflovxz.service.dto.TaskStatusDistributionDTO;
import com.protaskflovxz.service.mapper.TaskMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.protaskflovxz.domain.Task}.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        LOG.debug("Request to save Task : {}", taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) {
        LOG.debug("Request to update Task : {}", taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public Optional<TaskDTO> partialUpdate(TaskDTO taskDTO) {
        LOG.debug("Request to partially update Task : {}", taskDTO);

        return taskRepository
            .findById(taskDTO.getId())
            .map(existingTask -> {
                taskMapper.partialUpdate(existingTask, taskDTO);

                return existingTask;
            })
            .map(taskRepository::save)
            .map(taskMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Tasks");
        return taskRepository.findAll(pageable).map(taskMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskDTO> findOne(Long id) {
        LOG.debug("Request to get Task : {}", id);
        return taskRepository.findById(id).map(taskMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> findAllTasksForCurrentUser() {
        String currentUserLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException("Current user login not found"));
        LOG.debug("Request to get all Tasks for current user : {}", currentUserLogin);
        return taskRepository.findAllByAssignedToLogin(currentUserLogin).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskStatusDistributionDTO> getTaskStatusDistributionForCurrentUser() {
        String currentUserLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException("Current user login not found"));
        LOG.debug("Request to get Task status distribution for current user : {}", currentUserLogin);
        List<Object[]> distribution = taskRepository.countTasksByStatusForUser(currentUserLogin);

        return distribution
            .stream()
            .map(row -> {
                Boolean completed = (Boolean) row[0];
                Long count = (Long) row[1];
                String status = completed ? "Completed" : "Pending";
                return new TaskStatusDistributionDTO(status, count);
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TaskCompletionStatsDTO getTaskCompletionStatsForCurrentUser() {
        String currentUserLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException("Current user login not found"));
        LOG.debug("Request to get Task completion statistics for current user : {}", currentUserLogin);

        long totalTasks = taskRepository.countByAssignedToLogin(currentUserLogin);
        long completedTasks = taskRepository.countByAssignedToLoginAndCompleted(currentUserLogin, true);

        Double completionPercentage = (totalTasks == 0) ? 0.0 : (double) completedTasks / totalTasks * 100.0;

        return new TaskCompletionStatsDTO(totalTasks, completedTasks, completionPercentage);
    }
}
