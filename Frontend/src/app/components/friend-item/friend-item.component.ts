import { Component, Input, OnInit } from '@angular/core';
import { ImageService } from '../../services/ImageService';

@Component({
  selector: 'app-friend-item',
  templateUrl: './friend-item.component.html',
  styleUrls: ['./friend-item.component.css'],
})
export class FriendItemComponent implements OnInit {
  @Input() friend: any;
  postImageURL = '';

  constructor(public imageService: ImageService) {}

  async ngOnInit() {
    const userPicLocation = this.friend.friend.userPic;
    this.postImageURL = await this.imageService.getImage(userPicLocation);
  }

  removeFriend() {
    console.log('REMOVING FRIEND');
  }
}
