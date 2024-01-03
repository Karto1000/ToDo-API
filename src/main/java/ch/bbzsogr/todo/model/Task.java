package ch.bbzsogr.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    private String title;
    private String description;
    private boolean completed;

    @Column(name = "created_by", updatable = false)
    private int createdBy;
}
