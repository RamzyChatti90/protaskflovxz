package com.protaskflovxz.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for task completion statistics.
 */
public class TaskCompletionStatsDTO implements Serializable {

    private Long totalTasks;
    private Long completedTasks;
    private Double completionRate;

    public TaskCompletionStatsDTO() {
        // Empty constructor needed for Jackson deserialization
    }

    public TaskCompletionStatsDTO(Long totalTasks, Long completedTasks) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.completionRate = (totalTasks != null && totalTasks > 0) ? (double) completedTasks / totalTasks : 0.0;
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
        return Objects.equals(getTotalTasks(), that.getTotalTasks()) &&
               Objects.equals(getCompletedTasks(), that.getCompletedTasks()) &&
               Objects.equals(getCompletionRate(), that.getCompletionRate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotalTasks(), getCompletedTasks(), getCompletionRate());
    }

    @Override
    public String toString() {
        return "TaskCompletionStatsDTO{" +
               "totalTasks=" + totalTasks +
               ", completedTasks=" + completedTasks +
               ", completionRate=" + completionRate +
               '}' + "\n";
    }
}
