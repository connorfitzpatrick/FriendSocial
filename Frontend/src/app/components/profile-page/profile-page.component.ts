import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ProfileService } from '../../services/ProfileService';
import { MatDialog } from '@angular/material/dialog';
import { User } from '../../models/profile.model';
import { AuthService } from '../../services/AuthService';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit {
  private profileSubscription?: Subscription;
  private routeSubscription?: Subscription;
  userId: number = 0;
  username: string = '';
  userPic: string = '';
  bio: string = '';
  posts: any[] = [];
  isProfilePage: boolean = true;
  user!: User;

  constructor(
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router,
    private profileService: ProfileService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.username = this.authService.getHandle();
    // Fetch the user data and update the currentUserSubject
    this.profileSubscription = this.profileService
      .fetchLoggedInUserData(this.username)
      .subscribe(
        (user: User) => {
          this.authService.currentUserSubject.next(user);
        },
        (error) => {
          console.error('Error fetching user data:', error);
        }
      );

    // Get the username from the route parameters
    this.routeSubscription = this.route.params.subscribe((params) => {
      this.username = params['handle'];
      // Fetch profile information from the backend
    });
    this.fetchProfileInfo();
  }

  ngOnDestroy() {
    this.profileSubscription?.unsubscribe();
  }

  onMyProfileClick() {
    const myHandle = this.authService.getHandle();
    this.posts = [];
    this.router.navigateByUrl(`/profile/${myHandle}`);
  }

  async fetchProfileInfo() {
    // Make an HTTP request to fetch the profile information based on the username
    await this.profileService.fetchLoggedInUserData(this.username).subscribe(
      (data) => {
        this.userId = data.id;
        this.username = data.username;
        this.userPic = data.userPic;
        this.bio = data.bio;
        this.user = data;
      },
      (error) => {
        console.error('Error fetching profile information:', error);
      }
    );
  }
}
