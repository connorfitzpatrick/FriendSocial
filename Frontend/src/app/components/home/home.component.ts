import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/AuthService';
import { ProfileService } from '../../services/ProfileService';
import { User } from '../../models/profile.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  private profileSubscription?: Subscription;

  constructor(
    private authService: AuthService,
    private profileService: ProfileService
  ) {}

  ngOnInit() {
    const handle = this.authService.getHandle();
    // Fetch the user data and update the currentUserSubject
    this.profileSubscription = this.profileService
      .fetchLoggedInUserData(handle)
      .subscribe(
        (user: User) => {
          this.authService.currentUserSubject.next(user);
        },
        (error) => {
          console.error('Error fetching user data:', error);
        }
      );
  }

  ngOnDestroy() {
    console.log('destroyed');
    this.profileSubscription?.unsubscribe();
  }
}
