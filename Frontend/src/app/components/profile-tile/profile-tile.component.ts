import { Component, Input, OnInit, OnChanges } from '@angular/core';
import { User } from '../../models/profile.model';
import { AuthService } from '../../services/AuthService';
import { FriendService } from '../../services/FriendService';
import { ImageService } from '../../services/ImageService';
import { MatDialog } from '@angular/material/dialog';
import { EditDialogComponent } from '../../components/edit-dialog/edit-dialog.component';
import { SetupProfileDialogComponent } from '../../components/setup-profile-dialog/setup-profile-dialog.component';

@Component({
  selector: 'app-profile-tile',
  templateUrl: './profile-tile.component.html',
  styleUrls: ['./profile-tile.component.css'],
})
export class ProfileTileComponent implements OnInit, OnChanges {
  @Input() user: User | undefined;
  @Input() userId: number | undefined;
  isCurrentUserProfile: boolean = false;
  isFriend: number = -1;
  postImageURL = '';

  constructor(
    public dialog: MatDialog,
    private authService: AuthService,
    private friendService: FriendService,
    public imageService: ImageService
  ) {}

  async ngOnInit() {
    console.log(this.user);
    if (this.user) {
      this.postImageURL = await this.imageService.getImage(this.user.userPic);
    }
    const setupDialogShown = localStorage.getItem('setupDialogShown');
    if (setupDialogShown == 'false') {
      this.openProfileSetupDialog();
      localStorage.setItem('setupDialogShown', 'true');
    }
  }

  async ngOnChanges() {
    this.isCurrentUserProfile = this.authService.viewingProfile(this.userId);
    if (!this.isCurrentUserProfile && this.user) {
      this.postImageURL = await this.imageService.getImage(this.user.userPic);
    }
    this.updateFriendStatus();
  }

  async updateFriendStatus() {
    // Use friendservice method to check friend status based on userId
    this.isFriend = await this.friendService.checkFriendStatus(
      this.authService.getUserIdFromToken(),
      this.userId
    );
  }

  async toggleFriendship() {
    try {
      if (this.isFriend != -1) {
        // Unfriend logic
        await this.friendService.deleteFriend(this.userId);
        this.isFriend = -1;
      } else {
        // Friend logic
        await this.friendService.postFriend(
          this.authService.getUserIdFromToken(),
          this.userId
        );
        this.isFriend = 0;
      }
    } catch (error) {
      console.error('Error toggling friendship:', error);
    }
  }

  openCommentDialog(event: Event) {
    event.preventDefault();
    const dialogRef = this.dialog.open(EditDialogComponent, {
      width: '60%',
      maxWidth: '800px',
      autoFocus: false,
      panelClass: 'profile-edit-container',
      data: { user: this.user },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }

  openProfileSetupDialog() {
    const dialogRef = this.dialog.open(SetupProfileDialogComponent, {
      width: '60%',
      maxWidth: '800px',
      autoFocus: false,
      panelClass: 'profile-edit-container',
      data: { user: this.user, postImageUrl: this.postImageURL },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }
}
