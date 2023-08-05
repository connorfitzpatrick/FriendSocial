import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { BehaviorSubject, from, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../models/profile.model';
import { tap } from 'rxjs/operators';
import { ProfileService } from './ProfileService';
import { PostService } from './PostService';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1';
  public currentUserSubject: BehaviorSubject<User | null> =
    new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> =
    this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router,
    private profileService: ProfileService,
    private postService: PostService
  ) {}

  login(credentials: any): Observable<User> {
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

    return this.profileService.fetchLoggedInUserData(credentials.handle).pipe(
      tap((user: User) => {
        this.currentUserSubject.next(user);
      })
    );
  }

  register(user: any): Observable<User> {
    console.log(user);
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

    return this.profileService.fetchLoggedInUserData(user.handle).pipe(
      tap((user: User) => {
        this.currentUserSubject.next(user);
      })
    );
  }

  getUserIdFromToken(): number | null {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwt_decode(token);
      console.log(decodedToken.userId);
      return decodedToken.userId;
    }
    return null;
  }

  // Grabs the username/email from the authentication token
  getHandle(): string {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwt_decode<any>(token);
      return decodedToken.handle;
    }
    return '';
  }

  // Confirms whether the profile being routed to is owned by current user
  viewingProfile(userId: number | undefined): boolean {
    const currentUserId = this.getUserIdFromToken();
    console.log('currentUserId: ' + currentUserId);
    return currentUserId === userId;
  }

  onProfileClick(): void {
    this.postService.clearPosts(); // Clear the posts before navigating to the profile page
    this.router.navigate(['/profile', this.getHandle()]);
  }

  updateCurrentUser(user: User): void {
    this.currentUserSubject.next(user);
    // console.log(this.currentUserSubject);
  }
}
