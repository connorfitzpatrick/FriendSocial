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
import { throwError, Observable, BehaviorSubject } from 'rxjs';
import { filter } from 'rxjs';
import { take } from 'rxjs';

@Injectable()
export class TokenExpirationInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(
    null
  );

  constructor(private router: Router, private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 && !req.url.includes('/auth/refresh')) {
          if (this.isRefreshing) {
            // If another request is already refreshing the token, wait for it to complete
            return this.refreshTokenSubject.pipe(
              filter((result) => result !== null),
              take(1),
              switchMap(() => next.handle(this.addTokenToRequest(req)))
            );
          } else {
            this.isRefreshing = true;
            this.refreshTokenSubject.next(null);

            return this.authService.refreshToken().pipe(
              switchMap((refreshResponse) => {
                this.isRefreshing = false;
                this.refreshTokenSubject.next(refreshResponse);
                return next.handle(this.addTokenToRequest(req));
              }),
              catchError((refreshError) => {
                this.isRefreshing = false;
                console.error('Refresh token failed:', refreshError);
                this.router.navigate(['/login']);
                return throwError(refreshError);
              })
            );
          }
        }
        return throwError(error);
      })
    );
  }

  private addTokenToRequest(req: HttpRequest<any>): HttpRequest<any> {
    const authToken = localStorage.getItem('authenticationToken');
    if (authToken) {
      return req.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`,
        },
      });
    }
    return req;
  }
}
