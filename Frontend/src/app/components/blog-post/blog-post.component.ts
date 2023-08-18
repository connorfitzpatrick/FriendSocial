import { Component, Input, OnInit } from '@angular/core';
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
  @Input() post: any;
  @Input() userPic: any;
  postId!: number;
  likeCount: number = 0;
  isLiked: number = -1;
  postImageURL = '';
  userPicUrl = '';

  constructor(
    private likeService: LikeService,
    public imageService: ImageService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    public dialog: MatDialog
  ) {}

  async ngOnInit() {
    this.route.params.subscribe((params) => {
      // get the postId from route params and convert it to a number
      this.postId = this.post[0].id;
      this.likeCount = this.post[5];
    });
    const userPicLocation = this.post[1];
    this.userPicUrl = await this.imageService.getImage(userPicLocation);
    if (this.post[0].postType == 'Image') {
      this.getPostImage();
    }
    this.getPostIsLiked();
  }

  formatDate(timestamp: string | null): string {
    if (!timestamp) {
      return ''; // or any default value you prefer for null timestamps
    }

    const currentDate = new Date();
    const postDate = new Date(parseInt(timestamp) * 1000); // Convert seconds to milliseconds
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

  async getPostIsLiked(): Promise<void> {
    this.isLiked = await this.likeService.postIsLiked(this.postId);
  }

  async getPostImage(): Promise<void> {
    this.postImageURL = await this.imageService.getImage(this.post[0].imageUrl);
  }

  likePost(): void {
    const postId = this.post[0].id;

    if (this.isLiked == -1) {
      this.likeService.postLike(postId);
      // you should have this.isLiked = id in post response
      this.isLiked = 0;
      this.likeCount++;
    } else {
      this.likeService.deleteLike(postId);
      this.isLiked = -1;
      this.likeCount--;
    }
  }
}
