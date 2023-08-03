import { Component, OnInit, Input } from '@angular/core';
import { PostService } from '../../services/PostService';
import { User } from '../../models/profile.model';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css'],
})
export class FeedComponent implements OnInit {
  @Input() userId: number | undefined;
  @Input() user: User | undefined;
  posts: any[] = [];

  isCurrentUserProfile: boolean = false;
  @Input() isProfilePage: boolean = false;

  constructor(
    public postService: PostService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.fetchPosts();
  }

  isMyProfile(): boolean {
    if (this.user) {
      const isCurrentUserProfile = this.authService.viewingProfile(this.userId);
      return isCurrentUserProfile;
    }
    return false;
  }

  fetchPosts(): void {
    // If there is a userId, we are on a profile page. Grab only the posts from that userId
    if (this.userId) {
      this.isCurrentUserProfile = this.isMyProfile();
      this.postService.posts$.subscribe((posts) => {
        this.posts = posts
          .filter((post) => post.userId === this.userId)
          .map((post) => ({
            ...post,
            userPic: this.user?.userPic, // Set the userPic property for each post object
          }));
      });
      this.postService.fetchPostsByUserId(this.userId); // Trigger the fetching of posts by userId
    } else {
      // Default behavior, get all posts
      this.postService.fetchPosts(); // Trigger the fetching of all posts
    }
  }
}
