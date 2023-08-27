import { Component, OnInit, Input, Renderer2 } from '@angular/core';
import { PostService } from '../../services/PostService';
import { User } from '../../models/profile.model';
import { AuthService } from '../../services/AuthService';
import { ImageService } from '../../services/ImageService';
import { ElementRef, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css'],
})
export class FeedComponent implements OnInit, AfterViewInit {
  @Input() userId: number | undefined;
  @Input() user: User | undefined;
  posts: any[] = [];
  userPic: string | null = null;

  isCurrentUserProfile: boolean = false;
  @Input() isProfilePage: boolean = false;
  currentPage: number = 0;

  constructor(
    public postService: PostService,
    private authService: AuthService,
    private renderer: Renderer2,
    private imageService: ImageService,
    private elementRef: ElementRef
  ) {}

  async ngOnInit() {
    // this.fetchPosts();
    if (this.isProfilePage) {
      this.userPic = await this.imageService.getProfilePicUrl();
    }
    this.fetchPosts(this.currentPage);
  }

  ngAfterViewInit() {
    window.addEventListener('scroll', this.onScroll.bind(this));
  }

  isMyProfile(): boolean {
    if (this.user) {
      const isCurrentUserProfile = this.authService.viewingProfile(this.userId);
      return isCurrentUserProfile;
    }
    return false;
  }

  fetchPosts(page: number): void {
    // If there is a userId, we are on a profile page. Grab only the posts from that userId
    console.log('fetching posts at page ' + page);
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
      this.postService.fetchPosts(page); // Trigger the fetching of all posts
    }
  }

  onScroll(event: any): void {
    console.log('onScroll');

    // Vertical pixels scrolled
    const scrolled = window.scrollY;

    // Full height of the window's content
    const fullHeight = document.documentElement.scrollHeight;

    // Height of the visible window
    const windowHeight = window.innerHeight;

    // Check if we've scrolled to the bottom
    if (Math.ceil(scrolled + windowHeight) >= fullHeight) {
      this.currentPage++;
      this.fetchPosts(this.currentPage);
    }
  }
}
