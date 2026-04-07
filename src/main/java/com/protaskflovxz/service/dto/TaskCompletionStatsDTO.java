package com.protaskflovxz.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Task.
 * This entity has been updated to extend AbstractAuditingEntity to include auditing fields
 * like 'createdBy', 'createdDate', 'lastModifiedBy', and 'lastModifiedDate'.
 * This resolves the compilation error where 'createdBy' was used in TaskRepository queries
 * but was not defined in the Task entity itself.
 */
@Entity
@Table(name = "task")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Task extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "completed")
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Task title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Task description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public Task dueDate(Instant dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public Task completed(Boolean completed) {
        this.setCompleted(completed);
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task project(Project project) {
        this.setProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", completed='" + getCompleted() + "'" +
            ", createdBy='" + getCreatedBy() + "'" + // Include inherited field
            ", createdDate='" + getCreatedDate() + "'" + // Include inherited field
            ", lastModifiedBy='" + getLastModifiedBy() + "'" + // Include inherited field
            ", lastModifiedDate='" + getLastModifiedDate() + "'" + // Include inherited field
            "}";
    }
}