import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LikeService } from '../../services/LikeService';
import { CommentsService } from '../../services/CommentsService';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-comment-dialog',
  templateUrl: './comment-dialog.component.html',
  styleUrls: ['./comment-dialog.component.css'],
})
export class CommentDialogComponent implements OnInit {
  active: string = 'likes';
  likes: any[] = [];
  comments: any[] = [];
  commentContent: string = '';
  myId: number | null = this.authService.getUserIdFromToken();
  // Grab Id of post owner and use it to allow them to delete comments
  posterId: number | null = 0;
  userPic = '';

  constructor(
    public dialogRef: MatDialogRef<CommentDialogComponent>,
    public likeService: LikeService,
    public commentService: CommentsService,
    public authService: AuthService,
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
    console.log(this.commentService.comments$(this.data[0]));
  }

  postComment() {
    if (this.commentContent) {
      this.commentService.postComment(this.data[0], this.commentContent);
      console.log(this.comments);
    }
  }

  async deleteComment(commentId: number) {
    await this.commentService.deleteComment(commentId, this.data[0]);
    console.log(this.comments);
    this.comments = this.comments.filter(
      (comment) => comment[0].id !== commentId
    );
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
