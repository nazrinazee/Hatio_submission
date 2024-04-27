import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProjectCreateComponent } from './project-create/project-create.component';
import { ProjectDetailComponent } from './project-detail/project-detail.component';
import { LoginComponent } from './login/login.component';
import { ProjectViewComponent } from './project-view/project-view.component';

const routes: Routes = [
  //{ path: '', component: LoginComponent }, // Set login page as the default route
  { path: '', component: HomeComponent },
  //{ path: 'home', component: HomeComponent },
  { path: 'create', component: ProjectCreateComponent }, // Project Creation Page
  { path: 'projects/:id', component: ProjectDetailComponent }, // Project Detail Page
  { path: 'login', component: LoginComponent }, //ProjectViewComponent
  { path: 'projectss', component: ProjectViewComponent }, // Project List Page



  
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
