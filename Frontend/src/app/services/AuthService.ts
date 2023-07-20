import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  getUserIdFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwt_decode(token);
      return decodedToken.userId;
    }
    return null;
  }

  getUsername(): string | null {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwt_decode<any>(token);
      console.log('USERNAME IS ' + decodedToken.sub);
      return decodedToken.sub;
    }
    return null;
  }
}
