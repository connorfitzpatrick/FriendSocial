import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

/* This service handles API requests for viewing posts*/
@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiUrl = 'http://localhost:8080/api/v1/posts';

  constructor(private http: HttpClient) {}

  getPosts(): Observable<any[]> {
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
      }),
    };
    return this.http.get<any[]>(this.apiUrl, httpOptions);
  }
}
