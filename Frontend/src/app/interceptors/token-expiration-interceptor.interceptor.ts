import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpErrorResponse,
  HttpEvent,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../services/AuthService';
import { catchError, switchMap } from 'rxjs/operators';
import { throwError, Observable } from 'rxjs';

@Injectable()
export class TokenExpirationInterceptor implements HttpInterceptor {
  constructor(private router: Router, private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Unauthorized
          return this.authService.refreshToken().pipe(
            switchMap(() => {
              // Clone the original request and set the new token
              const newRequest = req.clone({
                headers: req.headers.set(
                  'Authorization',
                  `Bearer ${localStorage.getItem('refreshToken')}`
                ),
              });
              console.log(localStorage.getItem('refreshToken'));
              console.log(newRequest);
              return next.handle(newRequest);
            }),
            catchError((refreshError) => {
              console.error('Refresh token failed:', refreshError);
              // If refreshing the token also fails, logout and redirect to the login page
              this.router.navigate(['/login']);
              return throwError(refreshError);
            })
          );
        }
        return throwError(error);
      })
    );
  }
}
