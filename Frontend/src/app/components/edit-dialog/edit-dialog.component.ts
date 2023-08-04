import { Component, Input, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from '../../services/AuthService';
import { User } from '../../models/profile.model';

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

  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    public authService: AuthService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    // If data contains the user object, assign it to the component's user property
    this.user = this.data.user;
    console.log(this.user);
  }

  changeProfilePicture(): void {}

  applyChanges() {}

  cancel() {
    this.closeDialog();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
