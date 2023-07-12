import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommentsService } from '../../services/CommentsService';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-blog-post',
  templateUrl: './blog-post.component.html',
  styleUrls: ['./blog-post.component.css']
})
export class BlogPostComponent implements OnInit {
  postId!: number;
  @Input() post: any;
  @Input() comments: any[] = [];

  constructor(private http: HttpClient, private commentService: CommentsService, private route: ActivatedRoute) {}

  // ngOnInit is called after the component has been initialized and its Input has been bound
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      console.log(params);
      this.postId = this.post[0].id; // Get the postId from route params and convert it to a number
      console.log(this.postId);
      // Fetch the post data based on the postId
      this.commentService.getComments(this.postId).subscribe(comments => {
        this.comments = comments;
      });
      console.log(this.comments);
    });
  }
}
