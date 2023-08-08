import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  public commentsSubject: BehaviorSubject<any[]> = new BehaviorSubject<any[]>(
    []
  );

  constructor(private http: HttpClient) {}

  fetchComments(postId: number): void {
    const apiUrl = `http://localhost:8080/api/v1/comments/${postId}`;
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };
    this.http.get<any[]>(apiUrl, httpOptions).subscribe(
      (comments) => {
        this.commentsSubject.next(comments);
      },
      (error) => {
        console.error('Error fetching comments information:', error);
      }
    );
  }

  comments$(): Observable<any[]> {
    return this.commentsSubject.asObservable();
  }
}
