import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './AuthService';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, EMPTY } from 'rxjs';
import { ImageService } from './ImageService';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  private commentsSubjectsMap: Map<number, BehaviorSubject<any[]>> = new Map();

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private imageService: ImageService
  ) {}

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

    return this.http.get<any[]>(apiUrl, httpOptions);
  }

  async postComment(postId: number, commentContent: string) {
    const token = localStorage.getItem('token');
    const timestamp = new Date();
    const myId = this.authService.getUserIdFromToken();
    console.log(commentContent);

    const requestBody = {
      content: commentContent,
      timestamp: timestamp.toISOString(),
    };

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    try {
      const response = await this.http
        .post<any>(
          `http://localhost:8080/api/v1/comments/${myId}/${postId}`,
          requestBody,
          httpOptions
        )
        .toPromise();

      // new comment object for the BehaviorSubject
      const newCommentData = [
        response,
        this.authService.getHandle(),
        this.imageService.getProfilePicUrl(),
        myId,
        postId,
      ];

      // get this post's comments behaviorsubject
      const currentComments = this.commentsSubjectsMap.get(postId)?.value || [];
      const newComments = [...currentComments, newCommentData];
      this.commentsSubjectsMap.get(postId)?.next(newComments);
    } catch (error) {
      console.error('Error making POST request for new like:', error);
    }
  }

  async deleteComment(commentId: number, postId: number) {
    // we will need to get the ID of the like itself!!!!!!
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
      }),
    };

    try {
      const response = await this.http
        .delete<any>(
          `http://localhost:8080/api/v1/comments/${commentId}`,
          httpOptions
        )
        .toPromise();

      const postCommentsSubject = this.commentsSubjectsMap.get(postId);
      if (postCommentsSubject) {
        const currentComments = postCommentsSubject.value || [];
        const updatedComments = currentComments.filter(
          (comment) => comment.id !== commentId
        );
        postCommentsSubject.next(updatedComments);
      }
    } catch (error) {
      console.error('Error deleting comment:', error);
    }
  }
}
