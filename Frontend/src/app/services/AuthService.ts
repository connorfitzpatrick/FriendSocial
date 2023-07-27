import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  getUserIdFromToken(): number | null {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwt_decode(token);
      console.log(decodedToken.userId);
      return decodedToken.userId;
    }
    return null;
  }

  getUsername(): string | null {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwt_decode<any>(token);
      return decodedToken.sub;
    }
    return null;
  }

  viewingProfile(userId: number | undefined): boolean {
    console.log('UserId: ' + userId);

    const currentUserId = this.getUserIdFromToken();
    console.log('currentUserId: ' + currentUserId);
    return currentUserId === userId;
  }
}
