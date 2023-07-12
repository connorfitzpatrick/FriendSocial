import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  constructor(private http: HttpClient) {}

  getComments(postId: number): Observable<any[]> {
    const url = `http://localhost:8080/api/v1/comments/${postId}`;
    return this.http.get<any[]>(url);
  }
}