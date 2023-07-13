import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-comment-dialog',
  templateUrl: './comment-dialog.component.html',
  styleUrls: ['./comment-dialog.component.css']
})
export class CommentDialogComponent implements OnInit {
  comments: any[] = [];
  
  constructor(
    public dialogRef: MatDialogRef<CommentDialogComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit() {
    this.comments = this.data; // Assign the comments received from the parent component
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
