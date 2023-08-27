import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
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
  handle: string = '';
  password: string = '';
  dateJoined: number = Date.now();
  today: string;
  role: String = 'USER';
  bio: String | null = null;
  userPic: String | null = 'NullProfilePic.png';

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
  async onSubmitLogin() {
    const credentials = {
      username: this.login,
      password: this.password,
    };

    try {
      await this.authService.login(credentials);
    } catch (error) {
      console.error('Registration failed:', error);
    }
  }

  async onSubmitRegister() {
    const date = new Date();

    const user = {
      username: this.login,
      handle: this.handle,
      password: this.password,
      dob: this.dob,
      firstName: this.firstName,
      lastName: this.lastName,
      userPic: this.userPic,
      bio: this.bio,
      dateJoined: date.toISOString(),
      role: this.role,
    };

    try {
      await this.authService.register(user);
    } catch (error) {
      console.error('Registration failed:', error);
    }
  }
}
