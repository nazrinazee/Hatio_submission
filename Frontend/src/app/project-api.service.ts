import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectApiService {
  private apiUrl = 'http://localhost:8080/api/projects'; // Replace with your backend API URL

  constructor(private http: HttpClient) { }

  // Get all projects
  getAllProjects(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  // Get project by ID
  getProjectById(projectId: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${projectId}`);
  }

  // Create a new project
  createProject(projectData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, projectData);
  }

  // Update an existing project
  updateProject(projectId: string, projectData: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${projectId}`, projectData);
  }

  // Delete a project
  deleteProject(projectId: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${projectId}`);
  }
}
