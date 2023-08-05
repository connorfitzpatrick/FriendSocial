import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { PostService } from './PostService';
import { Injectable, ValueProvider } from '@angular/core';
import { User } from '../models/profile.model';
import { catchError } from 'rxjs/operators';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private apiUrl = 'http://localhost:8080/api/v1/users';

  constructor(private http: HttpClient, private postService: PostService) {}

  fetchLoggedInUserData(handle: string): Observable<any> {
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    this.postService.clearPosts();
    console.log(handle);
    return this.http.get<User>(
      `${this.apiUrl}/username/${handle}`,
      httpOptions
    );
  }

  updateUserData(updatedUser: any): void {
    const token = localStorage.getItem('token');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    console.log(updatedUser.id);
    console.log(`${this.apiUrl}/${updatedUser.id}`);

    this.http
      .put<any>(`${this.apiUrl}/${updatedUser.id}`, updatedUser, httpOptions)
      .subscribe(
        (response) => {
          // Handle the response from the backend, e.g., show a success message or redirect to login page
          const token = response.token;
          localStorage.setItem('token', token);
          console.log('Update successful:', response);
          // this.router.navigate(['/home']);
        },
        (error) => {
          // Handle the error, e.g., display an error message to the user
          console.error('Update failed:', error);
        }
      );

    // // Include the 'updatedUser' as the request body in the HTTP PUT request
    // return this.http
    //   .put<User>(`${this.apiUrl}/${updatedUser.id}`, updatedUser, httpOptions)
    //   .pipe(
    //     catchError((error) => {
    //       // Handle the error here
    //       console.error('Error updating user:', error);
    //       // Rethrow the error to propagate it to the calling component
    //       throw error;
    //     })
    //   );
  }
}
