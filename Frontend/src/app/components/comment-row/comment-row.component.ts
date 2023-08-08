import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CommentDialogComponent } from '../comment-dialog/comment-dialog.component';
import { LikeService } from '../../services/LikeService';
import { CommentsService } from '../../services/CommentsService';

@Component({
  selector: 'app-comment-row',
  templateUrl: './comment-row.component.html',
  styleUrls: ['./comment-row.component.css'],
})
export class CommentRowComponent {
  @Input() postId!: number;
  recentComments: any[] = [];

  showAllComments: boolean = false;
  threshold: number = 2; // Number of comments to display initially

  constructor(
    public dialog: MatDialog,
    private likeService: LikeService,
    private commentService: CommentsService
  ) {}

  toggleComments(): void {
    this.showAllComments = !this.showAllComments;
  }

  ngOnInit() {
    // Fetch likes data before opening the dialog
    // this.likeService.likes$().subscribe((likes) => {
    //   this.likes = likes;
    // });
    this.commentService.getRecentComments(this.postId).subscribe((comments) => {
      this.recentComments = comments;
    });
    console.log(this.recentComments);
  }

  openCommentDialog(event: Event) {
    event.preventDefault();
    const dialogRef = this.dialog.open(CommentDialogComponent, {
      width: '60%',
      maxWidth: '800px',
      autoFocus: false, // Ensure the option is set correctly
      panelClass: 'comment-dialog-container',
      data: [this.postId],
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }
}
