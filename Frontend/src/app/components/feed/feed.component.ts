import { Component, OnInit } from '@angular/core';
import { PostService } from '../../services/ViewPostService';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent {
  posts: any[] = [];
  // public getJsonValue: any;
  // public postJsonValue: any;
  constructor(private postService: PostService) { }
  
  ngOnInit() {
    this.postService.getPosts().subscribe(posts => {
      this.posts = posts;
    });
  }
}
