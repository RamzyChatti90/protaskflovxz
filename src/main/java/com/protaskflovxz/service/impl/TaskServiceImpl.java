package com.protaskflovxz.service.impl;

import com.protaskflovxz.domain.Task;
import com.protaskflovxz.domain.User;
import com.protaskflovxz.repository.TaskRepository;
import com.protaskflovxz.repository.UserRepository;
import com.protaskflovxz.security.SecurityUtils;
import com.protaskflovxz.service.TaskService;
import com.protaskflovxz.service.dto.TaskCompletionStatsDTO;
import com.protaskflovxz.service.dto.TaskDTO;
import com.protaskflovxz.service.dto.TaskStatusDistributionDTO;
import com.protaskflovxz.service.mapper.TaskMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final UserRepository userRepository; // Added for current user retrieval

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        LOG.debug("Request to save Task : {}", taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        // Assign current user if not already assigned (for new tasks)
        if (task.getAssignedTo() == null) {
            SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(task::setAssignedTo);
        }
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
    public Page<TaskDTO> findAllForCurrentUser(Pageable pageable) {
        LOG.debug("Request to get all Tasks for current user");
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isEmpty()) {
            return Page.empty(pageable);
        }
        Optional<User> currentUser = userRepository.findOneByLogin(currentUserLogin.get());
        return currentUser
            .map(user -> taskRepository.findByAssignedTo(user, pageable).map(taskMapper::toDto))
            .orElse(Page.empty(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskStatusDistributionDTO> getTaskStatusDistributionForCurrentUser() {
        LOG.debug("Request to get task status distribution for current user");
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isEmpty()) {
            return Collections.emptyList();
        }
        Optional<User> currentUser = userRepository.findOneByLogin(currentUserLogin.get());
        return currentUser
            .map(user ->
                taskRepository
                    .countTasksByStatusForUser(user)
                    .stream()
                    .map(result -> new TaskStatusDistributionDTO((String) result[0], (Long) result[1]))
                    .collect(Collectors.toList())
            )
            .orElse(Collections.emptyList());
    }

    @Override
    @Transactional(readOnly = true)
    public TaskCompletionStatsDTO getTaskCompletionStatsForCurrentUser() {
        LOG.debug("Request to get task completion stats for current user");
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isEmpty()) {
            return new TaskCompletionStatsDTO(0L, 0L);
        }
        Optional<User> currentUser = userRepository.findOneByLogin(currentUserLogin.get());
        return currentUser
            .map(user -> {
                Long totalTasks = taskRepository.countByAssignedTo(user);
                Long completedTasks = taskRepository.countByAssignedToAndStatus(user, "Completed"); // Assuming 'Completed' is a status
                return new TaskCompletionStatsDTO(totalTasks, completedTasks);
            })
            .orElse(new TaskCompletionStatsDTO(0L, 0L));
    }
}
