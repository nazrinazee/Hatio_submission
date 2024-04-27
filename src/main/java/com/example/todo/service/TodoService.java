package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entity.Project;
import com.example.todo.entity.Todo;
import com.example.todo.repository.ProjectRepository;
import com.example.todo.repository.TodoRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ProjectRepository projectRepository;
    
    public List<Todo> getTodosByProjectId(Long projectId) {
        return todoRepository.findByProjectId(projectId);
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    /*public Todo createTodo(Todo todo) {
        todo.setCreatedDate(LocalDateTime.now());
        return todoRepository.save(todo);
    }*/
    
    

    public Todo createTodo(Todo todoDto, Long projectId) {
        // Retrieve the Project entity from the database
    	Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + projectId + " not found"));
        // Create a new Todo entity and set its properties
        Todo todo = new Todo();
        todo.setDescription(todoDto.getDescription());
        todo.setStatus(false); // Assuming status is initially false for a new Todo
        todo.setCreatedDate(LocalDateTime.now()); // Set the created date to the current date/time
        todo.setUpdatedDate(null); // Set the updated date to null initially
        // Set the retrieved Project entity as the associated project for the Todo
        todo.setProject(project);
        
        // Save the Todo entity
        return todoRepository.save(todo);
    }


    public Todo updateTodo(Long id, Todo todoDetails) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setDescription(todoDetails.getDescription());
            todo.setStatus(todoDetails.getStatus());
            todo.setUpdatedDate(LocalDateTime.now());
            return todoRepository.save(todo);
        } else {
            return null; // Handle not found case
        }
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
    
    public boolean markTodoComplete(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo != null) {
            todo.setStatus(true);
            todo.setUpdatedDate(LocalDateTime.now());
            todoRepository.save(todo);
            return true;
        }
        return false;
    }

    public boolean markTodoPending(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo != null) {
            todo.setStatus(false);
            todo.setUpdatedDate(LocalDateTime.now());
            todoRepository.save(todo);
            return true;
        }
        return false;
    }
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }
}

