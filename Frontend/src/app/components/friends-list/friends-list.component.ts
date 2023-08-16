import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/AuthService';
import { FriendService } from '../../services/FriendService';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-friends-list',
  templateUrl: './friends-list.component.html',
  styleUrls: ['./friends-list.component.css'],
})
export class FriendsListComponent implements OnInit {
  searchTerm: string = '';
  suggestions: any = [];
  friendsList: any[] = [];

  constructor(
    private authService: AuthService,
    public friendService: FriendService
  ) {}

  async ngOnInit() {
    const userId = this.authService.getUserIdFromToken();
    console.log(this.friendService.friends$);
    await this.friendService.fetchFriends(userId);
    this.friendService.friends$.subscribe((friends) => {
      this.friendsList = friends;
      console.log(this.friendsList);
    });
  }

  search() {
    console.log('Searching');
  }

  selectSuggestion(suggestion: string) {}

  onSearchInputChange() {}

  listFriends() {}
}
