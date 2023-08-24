import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ProfileService } from '../../services/ProfileService';
import { MatDialog } from '@angular/material/dialog';
import { User } from '../../models/profile.model';
import { AuthService } from '../../services/AuthService';
import { SetupProfileDialogComponent } from '../../components/setup-profile-dialog/setup-profile-dialog.component';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit {
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

  ngOnInit(): void {
    // Get the username from the route parameters
    this.route.params.subscribe((params) => {
      this.username = params['handle'];
      // Fetch profile information from the backend
      this.fetchProfileInfo();
    });
    const setupDialogShown = localStorage.getItem('setupDialogShown');
    if (setupDialogShown == 'false') {
      console.log('opening');

      this.openProfileSetupDialog();
      console.log('dialog really should be opened');

      localStorage.setItem('setupDialogShown', 'true');
    } else {
      console.log('setupDialogShown should be true: ' + setupDialogShown);
    }
  }

  onMyProfileClick() {
    const myHandle = this.authService.getHandle();
    this.posts = [];
    this.router.navigateByUrl(`/profile/${myHandle}`);
  }

  fetchProfileInfo(): void {
    // Make an HTTP request to fetch the profile information based on the username
    this.profileService.fetchLoggedInUserData(this.username).subscribe(
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

  openProfileSetupDialog() {
    console.log('inside opendialog()');
    const dialogRef = this.dialog.open(SetupProfileDialogComponent, {
      width: '60%',
      maxWidth: '800px',
      autoFocus: false,
      panelClass: 'profile-edit-container',
      data: { user: this.user },
    });
    console.log('dialog should be opened');

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }
}
