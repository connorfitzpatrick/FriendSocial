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

  async login(credentials: any): Promise<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    try {
      const response = await this.http
        .post<any>(
          'http://localhost:8080/api/v1/auth/authenticate',
          credentials,
          httpOptions
        )
        .toPromise();

      const token = response.token;
      localStorage.setItem('token', token);
      this.router.navigate(['/home']);

      const fetchedUser = await this.profileService
        .fetchLoggedInUserData(credentials.handle)
        .toPromise();
      this.currentUserSubject.next(fetchedUser);

      return fetchedUser;
    } catch (error) {
      console.error('Registration failed:', error);
      throw error; // Re-throw the error to be handled by the caller
    }
  }

  async register(user: any): Promise<User> {
    console.log(user);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    try {
      const response = await this.http
        .post<any>(
          'http://localhost:8080/api/v1/auth/register',
          user,
          httpOptions
        )
        .toPromise();

      const token = response.token;
      localStorage.setItem('token', token);
      console.log('Registration successful:', response);
      this.router.navigate(['/home']);

      const fetchedUser = await this.profileService
        .fetchLoggedInUserData(user.handle)
        .toPromise();
      console.log('THIS RUNS AFTER REGISTRATION');
      this.currentUserSubject.next(fetchedUser);
      console.log(this.currentUserSubject);

      return fetchedUser;
    } catch (error) {
      console.error('Registration failed:', error);
      throw error; // Re-throw the error to be handled by the caller
    }

    // this.http
    //   .post<any>(
    //     'http://localhost:8080/api/v1/auth/register',
    //     user,
    //     httpOptions
    //   )
    //   .subscribe(
    //     (response) => {
    //       // Handle the response from the backend, e.g., show a success message or redirect to login page
    //       const token = response.token;
    //       localStorage.setItem('token', token);
    //       console.log('Registration successful:', response);
    //       this.router.navigate(['/home']);
    //     },
    //     (error) => {
    //       // Handle the error, e.g., display an error message to the user
    //       console.error('Registration failed:', error);
    //     }
    //   );

    // console.log('THIS RUNS AFTER REGISTRATION');
    // const vari = this.profileService.fetchLoggedInUserData(user.handle).pipe(
    //   tap((user: User) => {
    //     console.log('THIS DOESNT AFTER REGISTRATION');

    //     this.currentUserSubject.next(user);
    //     console.log(this.currentUserSubject);
    //   })
    // );
    // return vari;
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

  updateCurrentUser(user: any): void {
    this.currentUserSubject.next(user);
    console.log(this.currentUserSubject);
  }
}
