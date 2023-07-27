import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private apiUrl = 'http://localhost:8080/api/v1/users';

  constructor(private http: HttpClient) {}

  getUserProfile(username: string): Observable<any> {
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    return this.http.get<any>(
      `${this.apiUrl}/username/${username}`,
      httpOptions
    );
  }
}
