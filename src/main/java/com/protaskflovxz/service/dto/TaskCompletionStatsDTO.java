package com.protaskflovxz.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for task completion statistics.
 */
public class TaskCompletionStatsDTO implements Serializable {

    private Long totalTasks;
    private Long completedTasks;
    private Long pendingTasks;
    private Double completionRate;

    public TaskCompletionStatsDTO() {
        // Empty constructor needed for Jackson/Spring
    }

    public TaskCompletionStatsDTO(Long totalTasks, Long completedTasks, Long pendingTasks, Double completionRate) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
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

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskCompletionStatsDTO that = (TaskCompletionStatsDTO) o;
        return Objects.equals(totalTasks, that.totalTasks) &&
               Objects.equals(completedTasks, that.completedTasks) &&
               Objects.equals(pendingTasks, that.pendingTasks) &&
               Objects.equals(completionRate, that.completionRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTasks, completedTasks, pendingTasks, completionRate);
    }

    @Override
    public String toString() {
        return "TaskCompletionStatsDTO{" +
               "totalTasks=" + totalTasks +
               ", completedTasks=" + completedTasks +
               ", pendingTasks=" + pendingTasks +
               ", completionRate=" + completionRate +
               '}';
    }
}
