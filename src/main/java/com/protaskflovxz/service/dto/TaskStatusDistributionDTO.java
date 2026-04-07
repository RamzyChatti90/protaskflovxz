package com.protaskflovxz.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Task status distribution.
 */
public class TaskStatusDistributionDTO implements Serializable {

    private String status;
    private Long count;

    public TaskStatusDistributionDTO() {
        // Empty constructor needed for Jackson deserialization
    }

    public TaskStatusDistributionDTO(String status, Long count) {
        this.status = status;
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskStatusDistributionDTO)) {
            return false;
        }
        TaskStatusDistributionDTO that = (TaskStatusDistributionDTO) o;
        return Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getCount(), that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getCount());
    }

    @Override
    public String toString() {
        return "TaskStatusDistributionDTO{" +
               "status='" + status + '\'' +
               ", count=" + count +
               '}';
    }
}
