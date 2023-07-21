import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
  bio: String = 'c';
  profilePic: String = '././';

  constructor(private http: HttpClient, private router: Router) {
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

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    this.http
      .post<any>(
        'http://localhost:8080/api/v1/auth/authenticate',
        credentials,
        httpOptions
      )
      .subscribe(
        (response) => {
          // Handle the response from the backend
          // If the credentials are correct, you should receive a token in the response
          const token = response.token;
          // Save the token in your frontend, as a cookie in local storage
          localStorage.setItem('token', token);
          // Redirect the user to the desired page after successful login
          // You can use Angular Router to navigate to a different page
          // For example, navigate to the home page:
          // import { Router } from '@angular/router';
          // constructor(private router: Router) {}
          // this.router.navigate(['/home']);
          this.router.navigate(['/home']);
        },
        (error) => {
          // Handle the error, e.g., display an error message to the user
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
      profilePic: this.profilePic,
      bio: this.bio,
      dateJoined: date.toISOString(),
      role: this.role,
    };

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    this.http
      .post<any>(
        'http://localhost:8080/api/v1/auth/register',
        user,
        httpOptions
      )
      .subscribe(
        (response) => {
          // Handle the response from the backend, e.g., show a success message or redirect to login page
          const token = response.token;
          localStorage.setItem('token', token);
          console.log('Registration successful:', response);
          this.router.navigate(['/home']);
        },
        (error) => {
          // Handle the error, e.g., display an error message to the user
          console.error('Registration failed:', error);
        }
      );
  }
}
