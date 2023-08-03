import { Component, Input, OnChanges } from '@angular/core';
import { User } from '../../models/profile.model';
import { AuthService } from '../../services/AuthService';
import { MatDialog } from '@angular/material/dialog';
import { EditDialogComponent } from '../../components/edit-dialog/edit-dialog.component';

@Component({
  selector: 'app-profile-tile',
  templateUrl: './profile-tile.component.html',
  styleUrls: ['./profile-tile.component.css'],
})
export class ProfileTileComponent implements OnChanges {
  @Input() user: User | undefined;
  isCurrentUserProfile: boolean = false;

  constructor(public dialog: MatDialog, private authService: AuthService) {} // Inject the AuthService

  ngOnChanges(): void {
    console.log('IT ' + this.user?.userId);
    this.isCurrentUserProfile = this.authService.viewingProfile(
      this.user?.userId
    );
    console.log('IR ' + this.isCurrentUserProfile);
  }

  openCommentDialog(event: Event) {
    event.preventDefault();
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
