package com.project.todoapp.Controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.todoapp.Models.Project;
import com.project.todoapp.Models.Project.Todo;
import com.project.todoapp.Service.ProjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String getAllProjects(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn")) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "projects"; 
        }
        else{
            return "redirect:/login";
        }
    }

    @GetMapping("/new")
    public String getNewProjectForm(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn")) {

        return "newProject";
        }
        else{
            return "redirect:/login";
        }
    }

    @PostMapping("/new")
    public String createProject(@RequestParam String title) {
        projectService.createProject(title);
        return "redirect:/projects"; // Redirect to the projects page after creating a project
    }

    @GetMapping("/{id}")
    public String getProjectDetails(@PathVariable String id, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn")) {

        Project project = projectService.getProjectDetails(id);
        model.addAttribute("project", project);
        return "projectDetails"; 
        }
        else{
            return "redirect:/login";
        }
    }

    @PostMapping("/{id}/todo")
    public String addTodoToProject(@PathVariable String id, Todo todo) {
        todo.setStatus(Project.Status.PENDING); // Set initial status to "Pending"
        projectService.addTodoToProject(id, todo);
        return "redirect:/projects/{id}";
    }

    @PostMapping("/{projectId}/todo/{todoId}/delete")
    public String deleteTodoFromProject(@PathVariable String projectId, @PathVariable String todoId) {
        projectService.deleteTodoFromProject(projectId, todoId);
        return "redirect:/projects/{projectId}"; 
    }
    @PostMapping("/{projectId}/delete")
    public String deleteProject(@PathVariable String projectId) {
        projectService.deleteProject(projectId);
        return "redirect:/projects"; 
    }

    @PostMapping("/{projectId}/todo/{todoId}/markCompleted")
public String markTodoAsCompleted(@PathVariable String projectId, @PathVariable String todoId) {
    projectService.updateTodoInProject(projectId, todoId, Project.Status.COMPLETED);
    return "redirect:/projects/{projectId}#completedTasks";
}




@GetMapping("/{id}/export-summary")
public void exportProjectSummary(@PathVariable String id, HttpServletResponse response) {
    try {
        String markdownContent = projectService.exportProjectSummary(id);
        
        if (markdownContent == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Project not found");
            return;
        }
        
        response.setHeader("Content-Disposition", "attachment; filename=\"" + id + ".md\"");
        response.setContentType("text/markdown");
        
        response.getWriter().write(markdownContent);
    } catch (IOException e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        e.printStackTrace();
    }
}
@PostMapping("/{id}/update-title")
public String updateProjectTitle(@PathVariable String id, @RequestParam("newTitle") String newTitle) {
    projectService.updateProjectTitle(id, newTitle);
    return "redirect:/projects/{id}"; // Redirect to the project details page
}


}
