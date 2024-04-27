package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entity.Project;
import com.example.todo.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        project.setCreatedDate(LocalDateTime.now());
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setTitle(projectDetails.getTitle());
            return projectRepository.save(project);
        } else {
            return null; // Handle not found case
        }
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
