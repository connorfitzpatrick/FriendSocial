import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { AuthService } from '../../services/AuthService';
import { ProfileService } from '../../services/ProfileService';
import { ImageService } from '../../services/ImageService';

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
    public imageService: ImageService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.updateActiveMenuItem();

    this.authService.currentUser$.subscribe(async (user) => {
      if (user) {
        const userPicLocation = user.userPic; // Extract the userPic URL
        const userPicUrl = await this.imageService.getImage(userPicLocation);
        this.imageService.setProfilePicUrl(userPicUrl);
      }
    });
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
      this.route.snapshot.paramMap.get('handle') === this.getHandle();
    console.log(isOwnProfile);
    if (currentUrl.includes('/home')) {
      this.activeMenuItem = 'feed';
    } else if (currentUrl.includes('/profile') && isOwnProfile) {
      this.activeMenuItem = 'profile';
    } else {
      this.activeMenuItem = 'feed';
    }
  }

  // Grabs the username from the AuthService (AuthService will get from token)
  getHandle(): string | null {
    return this.authService.getHandle();
  }

  onProfileClick(): void {
    console.log('sidemenu runing');
    this.postService.clearPosts();
    this.router.navigate(['/profile', this.getHandle()]);
  }
}
