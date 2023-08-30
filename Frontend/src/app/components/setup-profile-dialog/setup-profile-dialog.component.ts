import { Component, ViewChild, Input, Inject, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from '../../services/AuthService';
import { ImageService } from '../../services/ImageService';
import { User } from '../../models/profile.model';
import { ProfileService } from '../../services/ProfileService';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-setup-profile-dialog',
  templateUrl: './setup-profile-dialog.component.html',
  styleUrls: ['./setup-profile-dialog.component.css'],
})
export class SetupProfileDialogComponent {
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
  profilePictureUrl: string | null = '';
  selectedUserPic: File | null = null;

  @ViewChild('fileInput', { static: false }) fileInput?: ElementRef;

  constructor(
    public dialogRef: MatDialogRef<SetupProfileDialogComponent>,
    public authService: AuthService,
    public imageService: ImageService,
    public profileService: ProfileService,
    private datePipe: DatePipe,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  async ngOnInit() {
    // If data contains the user object, assign it to the component's user property
    this.user = this.data.user;
    this.profilePictureUrl = await this.imageService.getProfilePicUrl();
  }

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
          .uploadImage(this.selectedUserPic, true)
          .toPromise();
        this.user.userPic = response.userPic;
      }

      // Convert Unix timestamp to milliseconds
      const dateJoined = new Date(Number(this.user.dateJoined) * 1000);
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
          const userPicLocation = this.user.userPic;
          const userPicUrl = await this.imageService.getImage(userPicLocation);
          this.imageService.setProfilePicUrl(userPicUrl);
          this.profilePictureUrl = this.imageService.getProfilePicUrl();
        }
      });
      console.log(updatedUser);
      this.profileService.updateUserData(updatedUser);
      this.authService.updateCurrentUser(updatedUser);
      this.closeDialog();
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
