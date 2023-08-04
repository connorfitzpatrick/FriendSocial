import {
  Component,
  ViewChild,
  Input,
  Inject,
  OnInit,
  ElementRef,
} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from '../../services/AuthService';
import { ImageService } from '../../services/ImageService';
import { User } from '../../models/profile.model';
import { ProfileService } from '../../services/ProfileService';

@Component({
  selector: 'app-edit-dialog',
  templateUrl: './edit-dialog.component.html',
  styleUrls: ['./edit-dialog.component.css'],
})
export class EditDialogComponent implements OnInit {
  @Input() user: User = {
    userId: 0,
    firstName: '',
    lastName: '',
    bio: '',
    handle: '',
    username: '',
    userPic: '',
    dob: '',
    dateJoined: '',
    password: '',
  };
  profilePictureUrl!: string;
  selectedUserPic: File | null = null;

  @ViewChild('fileInput', { static: false }) fileInput?: ElementRef;

  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    public authService: AuthService,
    public imageService: ImageService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.authService.currentUser$.subscribe((user) => {
      this.profilePictureUrl = user?.userPic || ''; // Use an empty string if user?.userPic is null or undefined
    });
  }

  ngOnInit(): void {
    // If data contains the user object, assign it to the component's user property
    this.user = this.data.user;
  }

  changeProfilePicture(): void {}

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];

    if (file) {
      // read the selected file and convert it into a data URL
      this.selectedUserPic = file;
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.profilePictureUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  // Send updated user info to backend
  applyChanges() {
    if (this.selectedUserPic) {
      console.log('Okay so it gets the pic...');
      this.imageService.uploadImage(this.selectedUserPic).subscribe(
        (response) => {
          console.log(response.userPic);
          this.user.userPic = response.userPic;
        },
        (error) => {
          // Handle error if the backend update fails
          console.error('Error updating profile picture:', error);
        }
      );
    }
    console.log(this.user.userPic);

    // this.profileService

    //
    this.authService.updateCurrentUser(this.user);
  }

  cancel() {
    this.closeDialog();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
