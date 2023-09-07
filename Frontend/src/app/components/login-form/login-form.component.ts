import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/AuthService';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent implements OnInit {
  loginForm!: FormGroup;
  registerForm!: FormGroup;
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
  public hasErrors: boolean = false;
  public errorMessage: string = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService,
    private fb: FormBuilder
  ) {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    const day = currentDate.getDate().toString().padStart(2, '0');
    this.today = `${year}-${month}-${day}`;
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });

    this.registerForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      handle: ['', Validators.required],
      dob: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onLoginTab(): void {
    this.active = 'login';
    this.hasErrors = false;
    this.errorMessage = '';
  }

  onRegisterTab(): void {
    this.active = 'register';
    this.hasErrors = false;
    this.errorMessage = '';
  }

  // Submit login event handler
  async onSubmitLogin() {
    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;
      try {
        await this.authService.login(credentials);
        this.hasErrors = false;
      } catch (error) {
        console.error('Login failed:', error);
        this.hasErrors = true;
        this.errorMessage = 'Login failed. Please try again.';
      }
    } else {
      this.hasErrors = true;
      this.errorMessage = 'Please fill out the form correctly.';
    }
  }

  async onSubmitRegister() {
    if (this.registerForm.valid) {
      const date = new Date();

      const user = {
        ...this.registerForm.value,
        dateJoined: new Date().toISOString(),
        role: this.role,
        userPic: this.userPic,
        bio: this.bio,
      };

      try {
        await this.authService.register(user);
        this.hasErrors = false;
      } catch (error) {
        console.error('Registration failed:', error);
        this.hasErrors = true;
        this.errorMessage = 'Registration failed. Please try again.';
      }
    } else {
      this.hasErrors = true;
      this.errorMessage = 'Please fill out the form correctly.';
    }
  }
}
