import { Component, Input, OnChanges } from '@angular/core';
import { User } from '../../models/profile.model';
import { AuthService } from '../../services/AuthService';
import { FriendService } from '../../services/FriendService';
import { ImageService } from '../../services/ImageService';
import { MatDialog } from '@angular/material/dialog';
import { EditDialogComponent } from '../../components/edit-dialog/edit-dialog.component';

@Component({
  selector: 'app-profile-tile',
  templateUrl: './profile-tile.component.html',
  styleUrls: ['./profile-tile.component.css'],
})
export class ProfileTileComponent implements OnChanges {
  @Input() user: User | undefined;
  @Input() userId: number | undefined;
  isCurrentUserProfile: boolean = false;
  isFriend: boolean = false;

  constructor(
    public dialog: MatDialog,
    private authService: AuthService,
    private friendService: FriendService,
    public imageService: ImageService
  ) {} // Inject the AuthService

  ngOnChanges(): void {
    this.isCurrentUserProfile = this.authService.viewingProfile(this.userId);
    this.updateFriendStatus();
  }

  async updateFriendStatus() {
    // Use a service method to check friend status based on userId
    this.isFriend = await this.friendService.checkFriendStatus(this.userId);
  }

  async toggleFriendship() {
    try {
      if (this.isFriend) {
        // Unfriend logic
        await this.friendService.deleteFriend(this.userId);
        this.isFriend = false;
      } else {
        // Friend logic
        await this.friendService.postFriend(
          this.authService.getUserIdFromToken(),
          this.userId
        );
        this.isFriend = true;
      }
    } catch (error) {
      console.error('Error toggling friendship:', error);
    }
  }

  openCommentDialog(event: Event) {
    event.preventDefault();
    console.log(this.user);
    const dialogRef = this.dialog.open(EditDialogComponent, {
      width: '60%',
      maxWidth: '800px',
      autoFocus: false, // Ensure the option is set correctly
      panelClass: 'profile-edit-container',
      data: { user: this.user },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }
}
