package ch.bbzsogr.todo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int taskId;

    @NotBlank(message = "title cannot be blank")
    @NotNull(message = "title must be sent")
    private String title;
    private String description;
    private boolean completed;

    @Column(name = "created_by", updatable = false)
    private int createdBy;
}
