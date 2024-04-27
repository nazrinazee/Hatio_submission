import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectApiService } from '../project-api.service';
import { TodoApiService } from '../todo-api.service';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.css']
})
export class ProjectDetailComponent implements OnInit {
  
  projectId: string = '';
  project: any = { title: '' };
  todos: any[] = [];
  newTodoDescription: string = '';
 

  constructor(private route: ActivatedRoute, private todoApiService: TodoApiService,private projectApiService: ProjectApiService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params['id'];
      
      if (this.projectId) {
        this.loadProject();
        this.loadTodos();
      }
    });
    
  }

  loadProject() {
    this.projectApiService.getProjectById(this.projectId).subscribe(
      (response: any) => {
        this.project = response;
        
      },
      (error) => {
        console.error('Error fetching project:', error);
      }
    );
  }

  loadTodos() {
    this.todoApiService.getTodosForProject(this.projectId).subscribe(
      (response: any[]) => {
        this.todos = response;
      },
      (error) => {
        console.error('Error fetching todos:', error);
      }
    );
  }



  addTodo() {
    if (!this.newTodoDescription) {
      return; // Don't add empty todos
    }
    const projectIdNumber: number = parseInt(this.projectId, 10);
    const todoData = {
      description: this.newTodoDescription,
      status: false, // By default, new todos are marked as pending
      createdDate: new Date().toISOString(),
      updatedDate: new Date().toISOString(),
      //projectId: this.projectId
      //project: { id: this.projectId }
    };

    this.todoApiService.addTodo(todoData,projectIdNumber).subscribe(
      (response: any) => {
        console.log('Todo added successfully:', response);
        this.loadTodos(); // Reload todos after adding a new one
        this.newTodoDescription = ''; // Clear the input field after adding todo
      },
      (error) => {
        console.error('Error adding todo:', error);
      }
    );
  }

  updateTodo(todoId: string, updatedTodo: any) {
    this.todoApiService.updateTodo(todoId, updatedTodo).subscribe(
      (response: any) => {
        // Optionally, handle success if needed
      },
      (error) => {
        console.error('Error updating todo:', error);
      }
    );
  }

  deleteTodo(todoId: string) {
    this.todoApiService.deleteTodo(todoId).subscribe(
      (response: any) => {
        this.loadTodos(); // Reload todos after deleting one
      },
      (error) => {
        console.error('Error deleting todo:', error);
      }
    );
  }

  markTodoComplete(todoId: string) {
    this.todoApiService.markTodoComplete(todoId).subscribe(
      (response: any) => {
        // Optionally, handle success if needed
        this.loadTodos();
        console.log('todo completed');
      },
      (error) => {
        console.error('Error marking todo as complete:', error);
      }
    );
  }

  markTodoPending(todoId: string) {
    this.todoApiService.markTodoPending(todoId).subscribe(
      (response: any) => {
        // Optionally, handle success if needed
        this.loadTodos();
        console.log('todo pending');
      },
      (error) => {
        console.error('Error marking todo as pending:', error);
      }
    );
  }

  

  updateProject() {
    this.projectApiService.updateProject(this.projectId,this.project).subscribe(
      (response: any) => {
        this.project = response;
        
      },
      (error) => {
        console.error('Error fetching project:', error);
      }
    );
  }
  
  exportSummary(projectId: number): void {
    const projectIdNum: number = parseInt(this.projectId, 10);
    this.todoApiService.exportSummary(projectIdNum).subscribe(
      response => {
        console.log('Gist created:', response);
        // Handle the response, e.g., show a success message to the user
      },
      error => {
        console.error('Failed to create gist:', error);
        // Handle the error, e.g., show an error message to the user
      }
    );
  }
}
