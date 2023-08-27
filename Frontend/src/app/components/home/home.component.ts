import {
  Component,
  OnInit,
  ElementRef,
  HostListener,
  ViewChild,
} from '@angular/core';
import { AuthService } from '../../services/AuthService';
import { ProfileService } from '../../services/ProfileService';
import { User } from '../../models/profile.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private profileService: ProfileService
  ) {}

  ngOnInit() {
    const handle = this.authService.getHandle();
    // Fetch the user data and update the currentUserSubject
    this.profileService.fetchLoggedInUserData(handle).subscribe(
      (user: User) => {
        this.authService.currentUserSubject.next(user);
      },
      (error) => {
        console.error('Error fetching user data:', error);
      }
    );
  }
}
