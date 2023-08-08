import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from './AuthService';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LikeService {
  public likesSubject: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);

  constructor(private http: HttpClient, private authService: AuthService) {}

  fetchLikes(postId: number): void {
    const apiUrl = `http://localhost:8080/api/v1/likes/${postId}`;
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    this.http.get<any[]>(apiUrl, httpOptions).subscribe(
      (likes) => {
        this.likesSubject.next(likes);
      },
      (error) => {
        console.error('Error fetching likes information:', error);
      }
    );
  }

  likes$(): Observable<any[]> {
    return this.likesSubject.asObservable();
  }

  async postLike(postId: number) {
    const token = localStorage.getItem('token');
    const timestamp = new Date();
    const myId = this.authService.getUserIdFromToken();

    const requestBody = {
      timestamp: timestamp.toISOString(),
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

      const newLikeData = {
        id: response,
        userId: myId,
        postId: postId,
        timestamp: timestamp,
      };

      const newLikes = [...this.likesSubject.value, newLikeData];
      this.likesSubject.next(newLikes);
    } catch (error) {
      console.error('Error making POST request for new like:', error);
    }
  }

  deleteLike(postId: number) {
    // we will need to get the ID of the like itself!!!!!!
    const token = localStorage.getItem('token');
    const myId = this.authService.getUserIdFromToken();

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
      }),
    };

    // const newLikes = this.likesSubject.value.filter(
    //   (like) => like.id !== deletedLikeId
    // );
    // this.likesSubject.next(newLikes);

    return this.http.delete<void>(
      `http://localhost:8080/api/v1/likes/${myId}/${postId}`,
      httpOptions
    );
  }
}
