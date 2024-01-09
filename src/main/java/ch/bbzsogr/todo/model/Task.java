package ch.bbzsogr.todo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int taskId;

    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be empty")
    private String title;
    private String description;
    private boolean completed;

    @Column(name = "created_by", updatable = false)
    private int createdBy;
}
