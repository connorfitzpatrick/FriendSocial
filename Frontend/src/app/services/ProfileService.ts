import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { PostService } from './PostService';
import { Injectable } from '@angular/core';
import { User } from '../models/profile.model';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private apiUrl = 'http://localhost:8080/api/v1/users';
  profilePictureUrl = '';

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

    console.log(updatedUser);

    this.http
      .put<any>(`${this.apiUrl}/${updatedUser.id}`, updatedUser, httpOptions)
      .subscribe(
        (response) => {
          // Handle the response from the backend, e.g., show a success message or redirect to login page
          // const token = response.token;
          // localStorage.setItem('token', token);
          console.log('Update successful:', response);
          // this.router.navigate(['/home']);
        },
        (error) => {
          // Handle the error, e.g., display an error message to the user
          console.error('Update failed:', error);
        }
      );
  }
}
