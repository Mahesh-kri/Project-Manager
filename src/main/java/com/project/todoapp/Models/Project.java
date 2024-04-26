package com.project.todoapp.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Project {

    @Id
    private String id;
    private String title;
    private List<Todo> todos;

    public Project() {
        todos = new ArrayList<>();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    // Get pending todos
    public List<Todo> getPendingTodos() {
        List<Todo> pendingTodos = new ArrayList<>();
        for (Todo todo : todos) {
            if (todo.getStatus() == Status.PENDING) {
                pendingTodos.add(todo);
            }
        }
        return pendingTodos;
    }

    // Get completed todos
    public List<Todo> getCompletedTodos() {
        List<Todo> completedTodos = new ArrayList<>();
        for (Todo todo : todos) {
            if (todo.getStatus() == Status.COMPLETED) {
                completedTodos.add(todo);
            }
        }
        return completedTodos;
    }

    // Nested class for Todo
    public static class Todo {
        private String id;
        private String description;
        private Status status;
        private Date createdDate;
        private Date updatedDate;
        // Getters and setters for Todo attributes
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Date getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
        }

        public Date getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(Date updatedDate) {
            this.updatedDate = updatedDate;
        }
    }

    // Enum for Todo status
    public enum Status {
        PENDING, COMPLETED
    }
}
