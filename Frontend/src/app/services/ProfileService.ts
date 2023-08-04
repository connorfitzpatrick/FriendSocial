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

  updateUserData(updatedUser: User): Observable<any> {
    const token = localStorage.getItem('token');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };
    return this.http.put<User>(
      `${this.apiUrl}/username/${updatedUser.userId}`,
      httpOptions
    );
  }
}
