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
    // Check if the user has a token (you'll need to implement your own token checking logic here)
    const hasToken = this.checkForToken();

    if (hasToken) {
      return true; // Allow access to the route
    } else {
      // Redirect to the login page
      this.router.navigate(['/login']);
      return false;
    }
  }

  private checkForToken(): boolean {
    const token = localStorage.getItem('token'); // Assuming you store the token in local storage
    return !!token; // Return true if the token is not null, undefined, or an empty string
  }
}
