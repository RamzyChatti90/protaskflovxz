package com.protaskflovxz.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for task completion statistics.
 */
public class TaskCompletionStatsDTO implements Serializable {

    private Long totalTasks;
    private Long completedTasks;
    private Double completionPercentage;

    public TaskCompletionStatsDTO() {
        // Empty constructor needed for Jackson/Spring
    }

    public TaskCompletionStatsDTO(Long totalTasks, Long completedTasks) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        if (totalTasks != null && totalTasks > 0) {
            this.completionPercentage = (double) completedTasks / totalTasks * 100.0;
        } else {
            this.completionPercentage = 0.0;
        }
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

    public Double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Double completionPercentage) {
        this.completionPercentage = completionPercentage;
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
        return (Objects.equals(totalTasks, that.totalTasks) &&
                Objects.equals(completedTasks, that.completedTasks) &&
                Objects.equals(completionPercentage, that.completionPercentage));
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTasks, completedTasks, completionPercentage);
    }

    @Override
    public String toString() {
        return "TaskCompletionStatsDTO{" +
               "totalTasks=" + totalTasks +
               ", completedTasks=" + completedTasks +
               ", completionPercentage=" + completionPercentage +
               '}';
    }
}
