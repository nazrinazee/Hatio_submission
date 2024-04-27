import { Component, OnInit } from '@angular/core';
import { ProjectApiService } from '../project-api.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project-create',
  templateUrl: './project-create.component.html',
  styleUrls: ['./project-create.component.css']
})
export class ProjectCreateComponent implements OnInit {

  newProject: any = {
    title: '',
   // createdDate: new Date().toISOString().slice(0, 10), // Automatically set to current date
    //createdDate: new Date().toISOString().slice(0, 19),
    //createdDate: new Date().toISOString(),
    //createdDate: new Date().toISOString().split('.')[0] + 'Z',
    //createdDate: new Date().toISOString(),
//  createdDate: new Date().toISOString().slice(0, 19).replace('T', ' '),
  };
  errorMessage: string = '';
  constructor(private projectApiService: ProjectApiService, private datePipe: DatePipe,private router: Router) { }

  ngOnInit(): void {
   
  }

  createProject() {
    //this.newProject.createdDate = this.datePipe.transform(new Date(), 'yyyy-MM-ddTHH:mm:ss');

    this.projectApiService.createProject(this.newProject).subscribe(
      (response: any) => {
        console.log('Project created successfully:', response);
        this.router.navigate(['/projectss']);
        // Optionally, you can navigate to a different page or display a success message
      },
      (error) => {
        console.error('Error creating project:', error);
        // Optionally, you can display an error message to the user
        this.errorMessage = 'Error creating project. Please try again.';
      }
    );
  }

  
}
