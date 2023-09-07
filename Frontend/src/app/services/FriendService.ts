import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './AuthService';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FriendService {
  public friendsSubject: BehaviorSubject<any[]> = new BehaviorSubject<any[]>(
    []
  );
  public friends$: Observable<any[]> = this.friendsSubject.asObservable();

  constructor(private http: HttpClient, private authService: AuthService) {}

  fetchFriends(userId: number) {
    const apiUrl = `http://localhost:8080/api/v1/friends/${userId}`;
    const token = localStorage.getItem('authenticationToken');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };
    this.http.get<any[]>(apiUrl, httpOptions).subscribe(
      (friends) => {
        console.log(friends);
        this.friendsSubject.next(friends);
      },
      (error) => {
        console.error('Error fetching friends information:', error);
      }
    );
    console.log(this.friendsSubject);
  }

  // create friendship with account
  async postFriend(userId: number | null, friendId?: number) {
    console.log('add friend');
    const token = localStorage.getItem('authenticationToken');
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

      // new like object for the BehaviorSubject
      const newFriend = {
        friend: response.friend,
      };

      const currentFriends = this.friendsSubject.getValue();
      const updatedFriends = [...currentFriends, newFriend];
      this.friendsSubject.next(updatedFriends);
    } catch (error) {
      console.error('Error making POST request for new like:', error);
    }
  }

  // Delete friendship with account
  async deleteFriend(friendId?: number) {
    const id = await this.checkFriendStatus(
      this.authService.getUserIdFromToken(),
      friendId
    );
    if (id !== -1) {
      const token = localStorage.getItem('authenticationToken');
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        }),
      };
      try {
        const response = await this.http
          .delete<any>(
            `http://localhost:8080/api/v1/friends/${id}`,
            httpOptions
          )
          .toPromise();

        // get this post's likes behaviorsubject
        const currentFriends = this.friendsSubject.value;
        const updatedFriends = currentFriends.filter(
          (friend: any) => friend.id !== id
        );
        this.friendsSubject.next(updatedFriends);
      } catch (error) {
        console.error('Error making DELETE request for new like:', error);
      }
    }
  }

  // Check if the logged in user is currently friends with a profile or not
  async checkFriendStatus(userId?: number | null, friendId?: number) {
    const token = localStorage.getItem('authenticationToken');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    try {
      const response = await this.http
        .get<any>(
          `http://localhost:8080/api/v1/friends/${userId}/${friendId}`,
          httpOptions
        )
        .toPromise();

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

  searchFriends(prefix: string): Observable<string[]> {
    const token = localStorage.getItem('authenticationToken');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };

    return this.http.get<string[]>(
      `http://localhost:8080/api/v1/search?query=${prefix}`,
      httpOptions
    );
  }
}
