import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommentsService } from '../../services/CommentsService';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-blog-post',
  templateUrl: './blog-post.component.html',
  styleUrls: ['./blog-post.component.css']
})
export class BlogPostComponent implements OnInit {
  postId!: number;
  @Input() post: any;
  @Input() comments: any[] = [];

  constructor(private http: HttpClient, private commentService: CommentsService, private route: ActivatedRoute, private datePipe: DatePipe) {}

  // ngOnInit is called after the component has been initialized and its Input has been bound
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.postId = this.post[0].id; // Get the postId from route params and convert it to a number
      // Fetch the post data based on the postId
      this.commentService.getComments(this.postId).subscribe(comments => {
        this.comments = comments;
      });
    });
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
  
}
