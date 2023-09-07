import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    // Check if the user has a token
    const hasToken = this.checkForToken();

    if (hasToken) {
      // Allow access to the route
      return true;
    } else {
      // Redirect to the login page
      this.router.navigate(['/login']);
      return false;
    }
  }

  private checkForToken(): boolean {
    // get token in local storage
    const token = localStorage.getItem('authenticationToken');
    // Return true if the token is not null, undefined, or an empty string
    return !!token;
  }
}
