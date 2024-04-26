package com.project.todoapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.todoapp.Models.Project;
import com.project.todoapp.Models.Project.Status;
import com.project.todoapp.Repository.ProjectRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(String title) {
        Project project = new Project();
        project.setTitle(title);
        return projectRepository.save(project);
    }

    @Override
    public Project updateProjectTitle(String id, String title) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setTitle(title);
        return projectRepository.save(project);
    }
    


    @Override
    public Project getProjectDetails(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    
    @Override
    public Project addTodoToProject(String id, Project.Todo todo) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        // Generate unique ID for the Todo
        todo.setId(UUID.randomUUID().toString());
        todo.setCreatedDate(new Date());
        
        project.getTodos().add(todo);
        return projectRepository.save(project);
    }

    @Override
    public Project updateTodoInProject(String projectId, String todoId,Project.Status status) {
        Project project = getProjectDetails(projectId);
        List<Project.Todo> todos = project.getTodos();
        for (Project.Todo todo : todos) {
            if (todo.getId().equals(todoId)) {
                todo.setStatus(status);
                todo.setDescription(todo.getDescription());
                todo.setUpdatedDate(new Date());
                break;
            }
        }
        return projectRepository.save(project);
    }

    @Override
    public Project deleteTodoFromProject(String projectId, String todoId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        List<Project.Todo> todos = project.getTodos();
        todos.removeIf(todo -> todo.getId().equals(todoId)); // Remove only the todo with the specified todoId\
    
        return projectRepository.save(project);
    }
    @Override
    public void deleteProject(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    
        projectRepository.delete(project);
    }
    
    



    @Override
    public String exportProjectSummary(String id) {
        Project project = projectRepository.findById(id)
                .orElse(null);
    
        if (project == null) {
            return null;
        }
    
        List<Project.Todo> completedTodos = getCompletedTodos(id);
        int totalTodos = project.getTodos().size();
    
        StringBuilder markdownContent = new StringBuilder();
        markdownContent.append("**").append(project.getTitle()).append("**\n\n");
        markdownContent.append("**Summary:** ").append(completedTodos.size()).append(" / ").append(totalTodos).append(" todos completed.\n\n");
    
        markdownContent.append("**Pending Todos**\n\n");
        project.getTodos().forEach(todo -> {
            if (todo.getStatus() == Status.PENDING) {
                markdownContent.append("- [ ] ").append(todo.getDescription()).append("\n");
            }
        });
    
        markdownContent.append("\n**Completed Todos**\n\n");
        completedTodos.forEach(todo -> {
            markdownContent.append("- [x] ").append(todo.getDescription()).append("\n");
        });
    
        return markdownContent.toString();
    }
    
    @Override
    public List<Project.Todo> getPendingTodos(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return project.getPendingTodos();
    }

    @Override
    public List<Project.Todo> getCompletedTodos(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return project.getCompletedTodos();
    }
}
