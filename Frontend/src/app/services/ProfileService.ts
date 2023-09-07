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

  fetchLoggedInUserData(input: string): Observable<any> {
    const token = localStorage.getItem('authenticationToken');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    this.postService.clearPosts();
    if (input.indexOf('@') !== -1) {
      return this.http.get<User>(`${this.apiUrl}/email/${input}`, httpOptions);
    } else {
      return this.http.get<User>(
        `${this.apiUrl}/username/${input}`,
        httpOptions
      );
    }
  }

  updateUserData(updatedUser: any): void {
    const token = localStorage.getItem('authenticationToken');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    this.http
      .put<any>(`${this.apiUrl}/${updatedUser.id}`, updatedUser, httpOptions)
      .subscribe(
        (response) => {
          // Handle the response from the backend
          console.log('Update successful:', response);
          // this.router.navigate(['/home']);
        },
        (error) => {
          console.error('Update failed:', error);
        }
      );
  }
}
