import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { AuthService } from './AuthService';
import { Observable, BehaviorSubject, EMPTY } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FriendService {
  public friendsSubject: BehaviorSubject<any | null> = new BehaviorSubject<
    any | null
  >(null);

  constructor(private http: HttpClient, private authService: AuthService) {}

  friends$(userId: number) {
    return this.fetchFriends(userId);
  }

  fetchFriends(userId: number): Observable<any[]> {
    const apiUrl = `http://localhost:8080/api/v1/friends/${userId}`;
    const token = localStorage.getItem('token');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };
    const response = this.http.get<any[]>(apiUrl, httpOptions);
    console.log(response);
    return response;
  }

  async checkFriendStatus(userId?: number | null, friendId?: number) {
    const token = localStorage.getItem('token');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    try {
      console.log(
        'Checking friendStatus from checkFriendStatus() in FriendService' +
          ' userID is: ' +
          userId +
          ' friendId is: ' +
          friendId
      );

      const response = await this.http
        .get<any>(
          `http://localhost:8080/api/v1/friends/${userId}/${friendId}`,
          httpOptions
        )
        .toPromise();
      console.log('Response ID is: ' + response.id);

      // at the moment this is returning true no matter what. must fix
      if (response) {
        if (response.status === 404) {
          return -1;
        } else {
          return response.id;
        }
      }
    } catch (error) {
      console.log('ISSUE GETTING FRIENDSHIP STATUS');
    }
    return -1;
  }

  async postFriend(userId: number | null, friendId?: number) {
    console.log('add friend');
    console.log('UserID: ' + userId + '. friendId: ' + friendId);
    const token = localStorage.getItem('token');
    const timestamp = new Date();

    const requestBody = {
      startDate: timestamp.toISOString(),
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
          `http://localhost:8080/api/v1/friends/${userId}/${friendId}`,
          requestBody,
          httpOptions
        )
        .toPromise();

      console.log(response);

      // new like object for the BehaviorSubject
      const newFriendData = {
        response,
      };

      // get this post's likes behaviorsubject
      const currentFriends = this.friendsSubject.value || [];
      const newFriends = [...currentFriends, newFriendData];
      this.friendsSubject.next(newFriends);
    } catch (error) {
      console.error('Error making POST request for new like:', error);
    }
  }

  async deleteFriend(friendId?: number) {
    console.log('delete friend');
    const id = await this.checkFriendStatus(
      this.authService.getUserIdFromToken(),
      friendId
    );
    var friendshipId = -1;
    if (id !== -1) {
      const token = localStorage.getItem('token');
      console.log(id);
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
        }),
      };
      try {
        console.log(id);
        const response = await this.http
          .delete<any>(
            `http://localhost:8080/api/v1/friends/${id}`,
            httpOptions
          )
          .toPromise();

        console.log(response);

        // new like object for the BehaviorSubject
        const newFriendData = {
          response,
        };

        // get this post's likes behaviorsubject
        const currentFriends = this.friendsSubject.value || [];
        const newLikes = [...currentFriends, newFriendData];
        this.friendsSubject.next(newLikes);
      } catch (error) {
        console.error('Error making POST request for new like:', error);
      }
    }
  }
}
