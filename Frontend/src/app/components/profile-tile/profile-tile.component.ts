import { Component, Input } from '@angular/core';
import { User } from '../../models/profile.model';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-profile-tile',
  templateUrl: './profile-tile.component.html',
  styleUrls: ['./profile-tile.component.css'],
})
export class ProfileTileComponent {
  @Input() userPic: string = '';
  @Input() user: User | undefined;

  @Input() isCurrentUserProfile: boolean | null = false;

  constructor(private authService: AuthService) {} // Inject the AuthService

  onEditProfile() {
    console.log('edit profile');
    console.log('this.user.userId');
  }
}
