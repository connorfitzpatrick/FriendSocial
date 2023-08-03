import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent {
  active: string = 'login';
  firstName: string = '';
  lastName: string = '';
  dob: string = '';
  login: string = '';
  password: string = '';
  dateJoined: number = Date.now();
  today: string; // To set the maximum date allowed
  role: String = 'USER';
  bio: String = 'ccccccc';
  userPic: String = 'https://i.imgur.com/PCXFcu2.jpg';

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    const day = currentDate.getDate().toString().padStart(2, '0');
    this.today = `${year}-${month}-${day}`;
  }

  onLoginTab(): void {
    this.active = 'login';
  }

  onRegisterTab(): void {
    this.active = 'register';
  }

  // Submit login event handler
  onSubmitLogin(): void {
    const credentials = {
      email: this.login,
      password: this.password,
    };

    // Call the login method from AuthService
    this.authService.login(credentials).subscribe(
      () => {
        // Redirect the user to the desired page after successful login
        this.router.navigate(['/home']);
      },
      (error) => {
        console.error('Login failed:', error);
      }
    );
  }

  onSubmitRegister(): void {
    const date = new Date();

    const user = {
      email: this.login,
      username: this.login,
      password: this.password,
      dob: this.dob,
      firstName: this.firstName,
      lastName: this.lastName,
      userPic: this.userPic,
      bio: this.bio,
      dateJoined: date.toISOString(),
      role: this.role,
    };

    // Call the login method from AuthService
    this.authService.register(user).subscribe(
      () => {
        // Redirect the user to the desired page after successful login
        this.router.navigate(['/home']);
      },
      (error) => {
        console.error('Registration failed:', error);
      }
    );
  }
}
