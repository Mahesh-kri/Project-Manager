package com.project.todoapp.Service;

import java.util.List;

import com.project.todoapp.Models.Project;
import com.project.todoapp.Models.Project.Todo;

public interface ProjectService {
    List<Project> getAllProjects();
    Project createProject(String title);
    Project updateProjectTitle(String id, String title);
    Project getProjectDetails(String id);
    Project addTodoToProject(String id, Todo todo);
    Project updateTodoInProject(String projectId, String todoId,Project.Status status);
    Project deleteTodoFromProject(String projectId, String todoId);
    void deleteProject(String projectId); 
    String exportProjectSummary(String id);
    List<Project.Todo> getPendingTodos(String projectId); 
    List<Project.Todo> getCompletedTodos(String projectId); 
}
