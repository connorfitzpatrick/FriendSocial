import { Component, Input, OnInit } from '@angular/core';
import { ImageService } from '../../services/ImageService';
import { FriendService } from '../../services/FriendService';
import { AuthService } from '../../services/AuthService';
import { FriendsListComponent } from '../friends-list/friends-list.component';

@Component({
  selector: 'app-friend-item',
  templateUrl: './friend-item.component.html',
  styleUrls: ['./friend-item.component.css'],
})
export class FriendItemComponent implements OnInit {
  @Input() friend: any;
  postImageURL = '';

  constructor(
    public imageService: ImageService,
    public friendService: FriendService,
    public authService: AuthService,
    public friendsListComponent: FriendsListComponent
  ) {}

  async ngOnInit() {
    const userPicLocation = this.friend.friend.userPic;
    this.postImageURL = await this.imageService.getImage(userPicLocation);
  }

  async removeFriend() {
    const userId = this.authService.getUserIdFromToken();
    console.log(this.friend.friend.id);
    await this.friendService.deleteFriend(this.friend.friend.id);
    this.friendsListComponent.friendsList =
      this.friendsListComponent.friendsList.filter(
        (friend) => friend.friend.id !== this.friend.friend.id
      );
  }
}
