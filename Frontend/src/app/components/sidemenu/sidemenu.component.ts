import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { AuthService } from '../../services/AuthService';
import { ProfileService } from '../../services/ProfileService';
import { PostService } from '../../services/PostService';

/*
    - ActivatedRoute respresents the current activated route in the app, provided to a component. It allows us to extract username from URL (route parameter)
    - NavigationEnd is an event triggered when navigation to a new route is completed. This can be used to set a loadingAmimation boolean to False when navigation
      is completed. In our case, it calls updateActiveMenuItem() when a new route is called.
*/

@Component({
  selector: 'app-sidemenu',
  templateUrl: './sidemenu.component.html',
  styleUrls: ['./sidemenu.component.css'],
})
export class SidemenuComponent implements OnInit {
  activeMenuItem!: string;

  constructor(
    public authService: AuthService,
    private postService: PostService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.updateActiveMenuItem();

    console.log(this.authService.currentUser$);

    // Subscribe to route changes
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.updateActiveMenuItem();
      }
    });
  }

  // keep track of active menu item
  private updateActiveMenuItem(): void {
    const currentUrl = this.router.routerState.snapshot.url;
    const isOwnProfile =
      this.route.snapshot.paramMap.get('username') === this.getUsername();
    console.log(isOwnProfile);
    if (currentUrl.includes('/home')) {
      this.activeMenuItem = 'feed';
    } else if (currentUrl.includes('/profile') && isOwnProfile) {
      this.activeMenuItem = 'profile';
    } else {
      this.activeMenuItem = 'feed';
    }
  }

  getUsername(): string | null {
    return this.authService.getUsername();
  }

  onProfileClick(): void {
    this.postService.clearPosts(); // Clear the posts before navigating to the profile page
    this.router.navigate(['/profile', this.getUsername()]);
  }
}
