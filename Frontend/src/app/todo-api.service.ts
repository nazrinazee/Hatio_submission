import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, switchMap, throwError } from 'rxjs';
import { ProjectApiService } from './project-api.service';

@Injectable({
  providedIn: 'root'
})
export class TodoApiService {
  private apiUrl = 'http://localhost:8080/api/todos'; // Replace with your backend API URL
  //private gistApiUrl = 'https://gist.github.com/939b239f2bb1a245a2ce660fbfa57eec.git';

  constructor(private http: HttpClient, private projectApiService: ProjectApiService) { }

  // Get all todos
  getTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Get all todos for a project
  getTodosForProject(projectId: string): Observable<any[]> {
    //return this.http.get<any[]>(`${this.apiUrl}/${projectId}`);
    return this.http.get<any[]>(`${this.apiUrl}/pro/${projectId}`);
  }

  // Add a new todo old backup
  //addTodo(todoData: any): Observable<any> {
  // return this.http.post<any>(this.apiUrl, todoData);
  //}
  addTodo(todoData: any, projectId: number): Observable<any> {
    const url = `${this.apiUrl}?projectId=${projectId}`;
    return this.http.post<any>(url, todoData);
  }

  // Update an existing todo
  updateTodo(todoId: string, todoData: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${todoId}`, todoData);
  }

  // Delete a todo
  deleteTodo(todoId: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${todoId}`);
  }

  // Mark a todo as complete
  markTodoComplete(todoId: string): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/${todoId}/complete`, { completed: true });
  }

  // Mark a todo as pending
  markTodoPending(todoId: string): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/${todoId}/pending`, { completed: false });
  }

 // exportSummary(projectId: number): Observable<any> {
    //return this.http.get<any>(`http://localhost:8080/api/gist/create?projectId=${projectId}`);
    
  //}

  exportSummary(projectId: number): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/api/gist/create/${projectId}`);
    //return this.http.get<any>('http://localhost:8080/api/gist/create', { projectId: projectId });
  }
  

}