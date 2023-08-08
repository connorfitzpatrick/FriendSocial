import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommentsService } from '../../services/CommentsService';
import { ImageService } from '../../services/ImageService';
import { LikeService } from '../../services/LikeService';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-blog-post',
  templateUrl: './blog-post.component.html',
  styleUrls: ['./blog-post.component.css'],
})
export class BlogPostComponent implements OnInit {
  postId!: number;
  likeCount: number = 0;
  @Input() post: any;
  @Input() userPic: any;
  comments: any[] = [];
  isLiked = false;
  postImageURL = '';

  constructor(
    private http: HttpClient,
    private commentService: CommentsService,
    private likeService: LikeService,
    public imageService: ImageService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      // get the postId from route params and convert it to a number
      this.postId = this.post[0].id;
      this.likeCount = this.post[5];
      // this.commentService.getComments(this.postId).subscribe((comments) => {
      //   this.comments = comments;
      // });
    });
    if (this.post[0].postType == 'Image') {
      this.getPostImage();
      console.log(this.post);
    }
  }

  formatDate(timestamp: string | null): string {
    if (!timestamp) {
      return ''; // or any default value you prefer for null timestamps
    }

    const currentDate = new Date();
    const postDate = new Date(timestamp);
    const difference = currentDate.getTime() - postDate.getTime();

    // Define the time intervals in milliseconds
    const minute = 60 * 1000;
    const hour = 60 * minute;
    const day = 24 * hour;

    if (difference < minute) {
      return 'Just now';
    } else if (difference < hour) {
      const minutesAgo = Math.floor(difference / minute);
      return `${minutesAgo} minute${minutesAgo > 1 ? 's' : ''} ago`;
    } else if (difference < day) {
      const hoursAgo = Math.floor(difference / hour);
      return `${hoursAgo} hour${hoursAgo > 1 ? 's' : ''} ago`;
    } else if (difference < 2 * day) {
      return 'Yesterday';
    } else {
      return this.datePipe.transform(postDate, 'mediumDate') || '';
    }
  }

  likePost(): void {
    const postId = this.post[0].id;

    if (!this.isLiked) {
      this.likeService.postLike(postId);
      this.isLiked = true;
      this.likeCount++;
    } else {
      this.likeService.deleteLike(postId);
      this.isLiked = false;
      this.likeCount--;
    }
  }

  async getPostImage(): Promise<void> {
    console.log('imageService being called again...' + this.postId);
    this.postImageURL = await this.imageService.getImage(this.post[0].imageUrl);
  }
}
