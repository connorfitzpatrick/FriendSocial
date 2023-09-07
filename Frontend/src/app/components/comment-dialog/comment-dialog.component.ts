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
    // Fetch comments and likes when dialog opens
    await this.likeService.fetchLikes(this.data[0]);
    this.commentService.fetchComments(this.data[0]);

    // Handle profile pic collection for likes
    this.likeService.likes$(this.data[0]).subscribe(async (likes) => {
      this.fetchProfileImages(likes, true);
    });

    // Handle profile pic collection for comments
    this.commentService.comments$(this.data[0]).subscribe(async (comments) => {
      this.fetchProfileImages(comments, false);
    });
  }

  // post comment to database
  async postComment() {
    if (this.commentContent) {
      await this.commentService.postComment(this.data[0], this.commentContent);
    }
  }

  // delete comment if you it is the user's post or comment
  async deleteComment(commentId: number) {
    await this.commentService.deleteComment(commentId, this.data[0]);
    this.comments = this.comments.filter(
      (comment) => comment[0].id !== commentId
    );
  }

  // fetch profile images for likes or comments
  private async fetchProfileImages(profiles: any[], forLikes: boolean) {
    const picPromises = profiles.map(async (p) => {
      if (p && typeof p[2] === 'string') {
        p.userPic = await this.imageService.getImage(p[2]);
      }
      return p;
    });

    // set this.likes or comments depending on forLikes boolean
    if (forLikes == true) {
      this.likes = await Promise.all(picPromises);
    } else {
      this.comments = await Promise.all(picPromises);
    }
  }

  // close dialog
  closeDialog(): void {
    this.dialogRef.close();
  }
}
