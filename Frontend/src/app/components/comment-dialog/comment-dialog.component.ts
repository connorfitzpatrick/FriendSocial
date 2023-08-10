import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LikeService } from '../../services/LikeService';
import { CommentsService } from '../../services/CommentsService';

@Component({
  selector: 'app-comment-dialog',
  templateUrl: './comment-dialog.component.html',
  styleUrls: ['./comment-dialog.component.css'],
})
export class CommentDialogComponent implements OnInit {
  active: string = 'likes';
  likes: any[] = [];
  comments: any[] = [];
  newComment: string = '';

  constructor(
    public dialogRef: MatDialogRef<CommentDialogComponent>,
    public likeService: LikeService,
    public commentService: CommentsService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onLikesTab(): void {
    this.active = 'likes';
  }

  onCommentsTab(): void {
    this.active = 'comments';
  }

  ngOnInit() {
    this.likeService.fetchLikes(this.data[0]);
    this.commentService.fetchComments(this.data[0]);

    this.likeService.likes$(this.data[0]).subscribe((likes) => {
      this.likes = likes;
    });

    this.commentService.comments$(this.data[0]).subscribe((comments) => {
      this.comments = comments;
    });

    console.log(this.comments);

    // this.comments = this.data[0];
  }

  postComment() {
    if (this.newComment) {
      // Assuming you have a service to post the comment, update the comments array, etc.
      // For demonstration purposes, let's assume you're adding the new comment directly to the comments array.
      this.comments.push({
        content: this.newComment,
        userId: 'user123', // Replace with the actual user ID
        userPic: 'path_to_user_pic.jpg', // Replace with the actual user picture URL
      });

      // Clear the new comment input field
      this.newComment = '';
    }
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
