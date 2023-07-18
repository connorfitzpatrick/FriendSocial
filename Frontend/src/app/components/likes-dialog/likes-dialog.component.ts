import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-likes-dialog',
  templateUrl: './likes-dialog.component.html',
  styleUrls: ['./likes-dialog.component.css']
})
export class LikesDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<LikesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public likes: any
  ) {}

  closeDialog(): void {
    this.dialogRef.close();
  }
}
