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
    console.log(this.postsSubject);
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

  async postPost(
    userId: number | null,
    postType: string,
    caption: string,
    imageUrl: string
  ) {
    const token = localStorage.getItem('token');
    const timestamp = new Date();
    // console.log(imageUrl);

    const requestBody = {
      postType: postType,
      content: caption,
      imageUrl: imageUrl,
      timestamp: timestamp.toISOString(),
    };

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
      }),
    };

    try {
      return await this.http
        .post<any>(
          `http://localhost:8080/api/v1/posts/${userId}`,
          requestBody,
          httpOptions
        )
        .toPromise();
    } catch (error) {
      console.error('Error making POST request for new post:', error);
    }
  }

  clearPosts(): void {
    // Clear the posts by emitting an empty array
    this.postsSubject.next([]);
  }
}
