import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { BehaviorSubject, catchError, Observable, of, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../models/profile.model';
import { ProfileService } from './ProfileService';
import { ImageService } from './ImageService';

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
    private imageService: ImageService
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

      const authToken = response.authenticationToken;
      localStorage.setItem('authenticationToken', authToken);
      this.setLoggedInUserData();
      const refreshToken = response.refreshToken;
      localStorage.setItem('refreshToken', refreshToken);

      const fetchedUser = await this.profileService
        .fetchLoggedInUserData(credentials.username)
        .toPromise();

      this.currentUserSubject.next(fetchedUser);

      if (
        fetchedUser.userPic == 'NullProfilePic.png' ||
        fetchedUser.bio == null
      ) {
        localStorage.setItem('setupDialogShown', 'false');
        this.router.navigate(['/profile', this.getHandle()]);
      } else {
        localStorage.setItem('setupDialogShown', 'true');
        this.router.navigate(['/home']);
      }

      this.currentUserSubject.next(fetchedUser);

      return fetchedUser;
    } catch (error) {
      console.error('Login failed:', error);
      throw error;
    }
  }

  async register(user: any): Promise<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    try {
      console.log(user);
      const response = await this.http
        .post<any>(
          'http://localhost:8080/api/v1/auth/register',
          user,
          httpOptions
        )
        .toPromise();
      const authToken = response.authenticationToken;
      localStorage.setItem('authenticationToken', authToken);
      this.setLoggedInUserData();
      const refreshToken = response.refreshToken;
      localStorage.setItem('refreshToken', refreshToken);

      const fetchedUser = await this.profileService
        .fetchLoggedInUserData(user.handle)
        .toPromise();
      this.currentUserSubject.next(fetchedUser);

      if (fetchedUser.userPic == null || fetchedUser.bio == null) {
        localStorage.setItem('setupDialogShown', 'false');
        this.router.navigate(['/profile', this.getHandle()]);
      } else {
        localStorage.setItem('setupDialogShown', 'true');
        this.router.navigate(['/home']);
      }

      return fetchedUser;
    } catch (error) {
      console.error('Registration failed:', error);
      throw error;
    }
  }

  refreshToken() {
    // or use a more specific type if possible
    const storedRefreshToken = localStorage.getItem('refreshToken');
    console.log('Stored Refresh Token:', storedRefreshToken);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('refreshToken')}`,
        'Refresh-Token': localStorage.getItem('refreshToken') || 'null',
      }),
    };

    const response = this.http
      .post<any>('http://localhost:8080/api/v1/auth/refresh', {}, httpOptions)
      .pipe(
        tap((response) => {
          localStorage.setItem(
            'authenticationToken',
            response.authenticationToken
          );
          console.log(response);
        }),
        catchError((error) => {
          console.error('Refresh token failed:', error);
          this.router.navigate(['/login']);
          return of(null);
        })
      );
    return response;
  }

  getUserIdFromToken(): number {
    const token = localStorage.getItem('authenticationToken');
    if (token) {
      const decodedToken: any = jwt_decode(token);
      return decodedToken.userId;
    }
    return -1;
  }

  // Grabs the username/email from the authentication token
  getHandle(): string {
    const token = localStorage.getItem('authenticationToken');
    if (token) {
      const decodedToken: any = jwt_decode<any>(token);
      return decodedToken.handle;
    }
    return '';
  }

  setLoggedInUserData() {
    const handle = this.getHandle();
    // Fetch the user data and update the currentUserSubject
    this.profileService.fetchLoggedInUserData(handle).subscribe(
      (user: User) => {
        this.currentUserSubject.next(user);
      },
      (error) => {
        console.error('Error fetching user data:', error);
      }
    );
  }

  // Confirms whether the profile being routed to is owned by current user
  viewingProfile(userId: number | undefined): boolean {
    const currentUserId = this.getUserIdFromToken();
    return currentUserId === userId;
  }

  onProfileClick(): void {
    // this.postService.clearPosts(); // Clear the posts before navigating to the profile page
    console.log('FAGGOTS');
    this.router.navigate(['/profile', this.getHandle()]);
  }

  updateCurrentUser(user: any): void {
    this.currentUserSubject.next(user);
    console.log(this.currentUserSubject);
  }

  async logout() {
    const token = localStorage.getItem('authenticationToken');
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
    };
    localStorage.removeItem('authenticationToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('setupDialogShown');
    this.imageService.setProfilePicUrl(null);

    const response = await this.http
      .post('http://localhost:8080/api/v1/auth/logout', null, httpOptions)
      .toPromise();
    this.router.navigate(['/login']);
  }
}
