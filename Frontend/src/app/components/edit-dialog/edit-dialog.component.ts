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
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-edit-dialog',
  templateUrl: './edit-dialog.component.html',
  styleUrls: ['./edit-dialog.component.css'],
})
export class EditDialogComponent implements OnInit {
  @Input() user: User = {
    id: 0,
    firstName: '',
    lastName: '',
    bio: '',
    handle: '',
    username: '',
    userPic: '',
    dob: '',
    dateJoined: '',
    password: '',
    role: '',
  };
  profilePictureUrl = this.imageService.getProfilePicUrl();
  selectedUserPic: File | null = null;

  @ViewChild('fileInput', { static: false }) fileInput?: ElementRef;

  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    public authService: AuthService,
    public imageService: ImageService,
    public profileService: ProfileService,
    private datePipe: DatePipe,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.authService.currentUser$.subscribe((user) => {
      this.profilePictureUrl = user?.userPic || ''; // Use an empty string if user?.userPic is null or undefined
    });
  }

  ngOnInit(): void {
    // If data contains the user object, assign it to the component's user property
    this.user = this.data.user;
    this.profilePictureUrl = this.imageService.getProfilePicUrl();
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
  async applyChanges() {
    try {
      if (this.selectedUserPic) {
        const response = await this.imageService
          .uploadImage(this.selectedUserPic)
          .toPromise();
        this.user.userPic = response.userPic;
      }

      const dateJoined = new Date(Number(this.user.dateJoined) * 1000); // Convert Unix timestamp to milliseconds
      const updatedUser = {
        id: this.user.id,
        username: this.user.username,
        handle: this.user.handle,
        password: this.user.password,
        dob: this.datePipe.transform(this.user.dob, 'yyyy-MM-dd'),
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        userPic: this.user.userPic,
        bio: this.user.bio,
        dateJoined: '2023-08-05T01:11:59.753Z',
        role: this.user.role,
      };

      this.authService.currentUser$.subscribe(async (user) => {
        if (user) {
          const userPicLocation = this.user.userPic; // Extract the userPic URL
          const userPicUrl = await this.imageService.getImage(userPicLocation);
          this.imageService.setProfilePicUrl(userPicUrl);
        }
      });

      console.log(updatedUser);
      this.profileService.updateUserData(updatedUser);

      console.log(updatedUser.userPic);

      //
      this.authService.updateCurrentUser(updatedUser);
    } catch (error) {
      // Handle error if the backend update fails
      console.error('Error updating profile picture:', error);
    }
  }

  cancel() {
    this.closeDialog();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
