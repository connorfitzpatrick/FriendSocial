import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';

/* This service handles API requests for viewing posts*/
@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiUrl = 'http://localhost:8080/api/v1/posts';
  public postsSubject: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
  public posts$: Observable<any[]> = this.postsSubject.asObservable();
  postIsLikedByUser = false;

  constructor(private http: HttpClient) {}

  fetchPosts(): void {
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    this.http.get<any[]>(this.apiUrl, httpOptions).subscribe(
      (posts) => {
        // Update the BehaviorSubject with new posts
        this.postsSubject.next(posts);
      },
      (error) => {
        console.error('Error fetching posts information:', error);
      }
    );
  }

  fetchPostsByUserId(id: number): void {
    const token = localStorage.getItem('token');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };
    this.http.get<any[]>(`${this.apiUrl}/${id}`, httpOptions).subscribe(
      (posts) => {
        // Update the BehaviorSubject with new posts
        this.postsSubject.next(posts);
      },
      (error) => {
        console.error('Error fetching posts information:', error);
      }
    );
  }

  clearPosts(): void {
    // Clear the posts by emitting an empty array
    this.postsSubject.next([]);
  }
}
