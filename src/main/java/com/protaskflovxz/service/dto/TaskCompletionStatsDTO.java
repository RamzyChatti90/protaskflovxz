package com.protaskflovxz.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.protaskflovxz.domain.Task} entity representing task completion statistics.
 */
public class TaskCompletionStatsDTO implements Serializable {

    private Long totalTasks;
    private Long completedTasks;
    private Long pendingTasks;
    private Long overdueTasks;
    private Double completionRate;

    public TaskCompletionStatsDTO() {
        // Empty constructor needed for Jackson/Spring
    }

    public TaskCompletionStatsDTO(Long totalTasks, Long completedTasks, Long pendingTasks, Long overdueTasks, Double completionRate) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.overdueTasks = overdueTasks;
        this.completionRate = completionRate;
    }

    public Long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public Long getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(Long completedTasks) {
        this.completedTasks = completedTasks;
    }

    public Long getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(Long pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public Long getOverdueTasks() {
        return overdueTasks;
    }

    public void setOverdueTasks(Long overdueTasks) {
        this.overdueTasks = overdueTasks;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskCompletionStatsDTO)) {
            return false;
        }
        TaskCompletionStatsDTO that = (TaskCompletionStatsDTO) o;
        return (
            Objects.equals(getTotalTasks(), that.getTotalTasks()) &&
            Objects.equals(getCompletedTasks(), that.getCompletedTasks()) &&
            Objects.equals(getPendingTasks(), that.getPendingTasks()) &&
            Objects.equals(getOverdueTasks(), that.getOverdueTasks()) &&
            Objects.equals(getCompletionRate(), that.getCompletionRate())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotalTasks(), completedTasks, pendingTasks, overdueTasks, getCompletionRate());
    }

    @Override
    public String toString() {
        return "TaskCompletionStatsDTO{" +
            "totalTasks=" + totalTasks +
            ", completedTasks=" + completedTasks +
            ", pendingTasks=" + pendingTasks +
            ", overdueTasks=" + overdueTasks +
            ", completionRate=" + completionRate +
            '}';
    }
}
