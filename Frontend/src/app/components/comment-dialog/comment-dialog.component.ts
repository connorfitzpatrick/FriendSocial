import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LikeService } from '../../services/LikeService';
import { CommentsService } from '../../services/CommentsService';
import { AuthService } from '../../services/AuthService';
import { ImageService } from '../../services/ImageService';

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
    public imageService: ImageService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  onLikesTab(): void {
    this.active = 'likes';
  }

  onCommentsTab(): void {
    this.active = 'comments';
  }

  async ngOnInit() {
    console.log(this.data);

    await this.commentService.fetchComments(this.data[0]);
    await this.likeService.fetchLikes(this.data[0]);

    await this.likeService.likes$(this.data[0]).subscribe(async (likes) => {
      await this.fetchProfileImages(likes, this.comments);
      console.log(this.likes);
    });

    await this.commentService
      .comments$(this.data[0])
      .subscribe(async (comments) => {
        await this.fetchProfileImages(this.likes, comments);
        console.log(this.comments);
      });
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

  private async fetchProfileImages(likes: any[], comments: any[]) {
    console.log(likes);
    const likePromises = likes.map(async (like) => {
      if (like && typeof like[2] === 'string') {
        like.userPic = await this.imageService.getImage(like[2]);
      }
      return like;
    });

    const commentPromises = comments.map(async (comment) => {
      if (comment && typeof comment[2] === 'string') {
        comment.userPic = await this.imageService.getImage(comment[2]);
      }
      return comment;
    });

    this.likes = await Promise.all(likePromises);
    this.comments = await Promise.all(commentPromises);

    console.log(this.likes);
    console.log(this.comments);
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
