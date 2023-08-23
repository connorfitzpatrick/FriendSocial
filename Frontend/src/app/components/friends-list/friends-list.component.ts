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
  userInput: string = '';
  searchSuggestions: any = [];
  friendsList: any[] = [];

  constructor(
    private authService: AuthService,
    public friendService: FriendService
  ) {}

  async ngOnInit() {
    const userId = this.authService.getUserIdFromToken();
    await this.friendService.fetchFriends(userId);
    this.friendService.friends$.subscribe((friends) => {
      this.friendsList = friends;
      console.log(this.friendsList);
    });
  }

  search() {
    console.log('Searching');
  }

  selectSuggestion(suggestion: string) {
    console.log('SELECTED SUGGESTION');
  }

  onSearchInputChange() {
    console.log(this.userInput);
    this.friendService
      .searchFriends(this.userInput)
      .subscribe((suggestions) => {
        this.searchSuggestions = suggestions;
      });
    console.log(this.searchSuggestions);
  }

  listFriends() {}
}
