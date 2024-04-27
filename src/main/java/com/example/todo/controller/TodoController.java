package com.example.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.todo.entity.Todo;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.service.TodoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/pro/{projectId}")
    public List<Todo> getTodosByProjectId(@PathVariable Long projectId) {
        return todoService.getTodosByProjectId(projectId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
    	Optional<Todo> optionalTodo = todoService.getTodoById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get(); // Extract Todo from Optional
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo,@RequestParam Long projectId) {
        Todo createdTodo = todoService.createTodo(todo,projectId);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todoDetails);
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 // Mark a todo as complete
    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<Void> markTodoComplete(@PathVariable Long todoId) {
        boolean success = todoService.markTodoComplete(todoId);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Mark a todo as pending
    @PatchMapping("/{todoId}/pending")
    public ResponseEntity<Void> markTodoPending(@PathVariable Long todoId) {
        boolean success = todoService.markTodoPending(todoId);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {
        List<Todo> todos = todoService.getTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
}
