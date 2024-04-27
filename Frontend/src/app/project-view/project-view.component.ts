import { Component, OnInit } from '@angular/core';
import { ProjectApiService } from '../project-api.service';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-project-view',
  templateUrl: './project-view.component.html',
  styleUrls: ['./project-view.component.css']
})
export class ProjectViewComponent implements OnInit {
  projects: any[] = [];

  constructor(private projectApiService: ProjectApiService, private router: Router,private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.getProjects();
    this.projects.forEach(project => {
      // Format the date using DatePipe
      project.createdDate = this.datePipe.transform(project.createdDate, 'yyyy-MM-dd HH:mm:ss');
  });
  }
  
  getProjects() {
    this.projectApiService.getAllProjects().subscribe(
      (response: any) => {
        console.log(response.data);
        // Check if the response contains a 'data' property
        if (response && response.data) {
          // Assuming the data property contains the projects array
          this.projects = response.data;
          console.log('Projects:', this.projects);
        } else {
          console.error('Invalid response format. Expected property "data".', response);
        }
      },
      (error) => {
        console.error('Error fetching projects:', error);
      }
    );
  }
  goToProjectDetail(projectId: string) {
    this.router.navigate(['/projects/:id', projectId]); // Navigate to the project detail page with the project ID
  }

  deleteProject(projectId: string) {
    this.projectApiService.deleteProject(projectId).subscribe(
      (response: any) => {
        console.log('Project deleted successfully:', response);
        // Optionally, you can update the projects list or display a success message
        this.getProjects(); // Refresh the projects list
      },
      (error) => {
        console.error('Error deleting project:', error);
        // Optionally, you can display an error message to the user
      }
    );
  }

  viewProject(projectId: string) {
    this.router.navigate(['/projects/', projectId]); // Navigate to the project detail page with the project ID
  }
}
