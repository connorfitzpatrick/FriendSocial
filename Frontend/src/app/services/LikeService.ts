import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './AuthService';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, EMPTY } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LikeService {
  private likesSubjectsMap: Map<number, BehaviorSubject<any[]>> = new Map();

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
        this.likesSubjectsMap.get(postId)?.next(likes);
      },
      (error) => {
        console.error('Error fetching likes information:', error);
      }
    );
  }

  likes$(postId: number): Observable<any[]> {
    if (!this.likesSubjectsMap.has(postId)) {
      this.likesSubjectsMap.set(postId, new BehaviorSubject<any[]>([]));
      this.fetchLikes(postId);
    }
    return this.likesSubjectsMap.get(postId)?.asObservable() ?? EMPTY;
  }

  async postIsLiked(postId: number): Promise<number> {
    const token = localStorage.getItem('token');
    const myId = await this.authService.getUserIdFromToken();
    var isLiked = -1;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
      }),
    };

    try {
      const response = await this.http
        .get<any>(
          `http://localhost:8080/api/v1/likes/isLiked/${postId}/${myId}`,
          httpOptions
        )
        .toPromise();
      console.log(response);
      isLiked = response;
    } catch (error) {
      console.error('Error making request for if post isLiked:', error);
    }
    return isLiked;
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

      // new like object for the BehaviorSubject
      const newLikeData = {
        id: response,
        userId: myId,
        postId: postId,
        timestamp: timestamp,
      };

      // get this post's likes behaviorsubject
      const currentLikes = this.likesSubjectsMap.get(postId)?.value || [];
      const newLikes = [...currentLikes, newLikeData];
      this.likesSubjectsMap.get(postId)?.next(newLikes);
    } catch (error) {
      console.error('Error making POST request for new like:', error);
    }
  }

  async deleteLike(postId: number) {
    // we will need to get the ID of the like itself!!!!!!
    const token = localStorage.getItem('token');
    const likeIdPromise = await this.postIsLiked(postId);
    const likeId = likeIdPromise;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
      }),
    };

    const response = await this.http
      .delete<any>(`http://localhost:8080/api/v1/likes/${likeId}`, httpOptions)
      .toPromise();
  }
}
