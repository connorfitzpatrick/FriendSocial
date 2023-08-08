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
    this.likeService.fetchLikes(this.data[1]);
    this.commentService.fetchComments(this.data[1]);

    this.likeService.likes$().subscribe((likes) => {
      this.likes = likes;
    });
    this.commentService.comments$().subscribe((comments) => {
      this.comments = comments;
    });

    console.log(this.likes);
    console.log(this.comments);

    // this.comments = this.data[0];
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
