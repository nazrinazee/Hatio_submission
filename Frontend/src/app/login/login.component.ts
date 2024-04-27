import { Component } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService) { }

  login() {
    this.authService.login(this.username, this.password).subscribe(
      () => {
        // Redirect to the home page or any other page upon successful login
      },
      (error:any) => {
        this.errorMessage = error.message; // Display error message
      }
    );
  }
}
