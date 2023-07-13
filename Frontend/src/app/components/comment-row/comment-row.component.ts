import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CommentDialogComponent } from '../comment-dialog/comment-dialog.component';


@Component({
  selector: 'app-comment-row',
  templateUrl: './comment-row.component.html',
  styleUrls: ['./comment-row.component.css']
})
export class CommentRowComponent {
  @Input() comments: any[] = [];
  showAllComments: boolean = false;
  threshold: number = 2; // Number of comments to display initially

  constructor(public dialog: MatDialog) {}

  toggleComments(): void {
    this.showAllComments = !this.showAllComments;
  }

  openCommentDialog(event: Event) {
    event.preventDefault();
    const dialogRef = this.dialog.open(CommentDialogComponent, {
      width: '60%',
      maxWidth: '800px',
      autoFocus: false, // Ensure the option is set correctly
      panelClass: 'comment-dialog-container',
      data: this.comments
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}

