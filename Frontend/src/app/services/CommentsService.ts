import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, EMPTY } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  private commentsSubjectsMap: Map<number, BehaviorSubject<any[]>> = new Map();

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
        this.commentsSubjectsMap.get(postId)?.next(comments);
      },
      (error) => {
        console.error('Error fetching comments information:', error);
      }
    );
  }

  comments$(postId: number): Observable<any[]> {
    if (!this.commentsSubjectsMap.has(postId)) {
      this.commentsSubjectsMap.set(postId, new BehaviorSubject<any[]>([]));
      this.fetchComments(postId);
    }
    return this.commentsSubjectsMap.get(postId)?.asObservable() ?? EMPTY;
  }

  getRecentComments(postId: number): Observable<any[]> {
    const apiUrl = `http://localhost:8080/api/v1/comments/${postId}/recent-comments`;
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    console.log('Getting comments for post ' + postId);
    return this.http.get<any[]>(apiUrl, httpOptions);
  }
}
