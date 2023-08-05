import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './AuthService';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LikeService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getLikes(postId: number): Observable<any[]> {
    const apiUrl = `http://localhost:8080/api/v1/likes/${postId}`;
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
      }),
    };
    return this.http.get<any[]>(apiUrl, httpOptions);
  }

  async postLike(postId: number) {
    const token = localStorage.getItem('token');
    const timestamp = new Date();
    const myId = this.authService.getUserIdFromToken();

    const requestBody = {
      // Other data you want to include in the request body
      timestamp: timestamp.toISOString(), // Convert the timestamp to the desired format
    };

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
      }),
    };

    try {
      const response = await this.http
        .post<any>(
          `http://localhost:8080/api/v1/likes/${myId}/${postId}`,
          requestBody,
          httpOptions
        )
        .toPromise();
    } catch (error) {
      console.error('Error making POST request:', error);
    }
  }
}
