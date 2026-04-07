package com.protaskflovxz.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class TaskStatusDistributionDTO implements Serializable {

    private String status;
    private Long count;
    private String createdBy;

    public TaskStatusDistributionDTO() {
        // Empty constructor needed for Jackson deserialization
    }

    public TaskStatusDistributionDTO(String status, Long count) {
        this.status = status;
        this.count = count;
    }

    public TaskStatusDistributionDTO(String status, Long count, String createdBy) {
        this.status = status;
        this.count = count;
        this.createdBy = createdBy;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskStatusDistributionDTO that = (TaskStatusDistributionDTO) o;
        return Objects.equals(status, that.status) && Objects.equals(count, that.count) && Objects.equals(createdBy, that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, count, createdBy);
    }

    @Override
    public String toString() {
        return "TaskStatusDistributionDTO{" +
               "status='" + status + '\'' +
               ", count=" + count +
               ", createdBy='" + createdBy + '\'' +
               '}';
    }
}