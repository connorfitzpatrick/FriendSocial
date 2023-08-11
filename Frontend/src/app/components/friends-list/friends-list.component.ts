import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/AuthService';
import { FriendService } from '../../services/FriendService';

@Component({
  selector: 'app-friends-list',
  templateUrl: './friends-list.component.html',
  styleUrls: ['./friends-list.component.css'],
})
export class FriendsListComponent implements OnInit {
  searchTerm: string = '';
  suggestions: any = [];
  friends: any = [];

  constructor(
    private authService: AuthService,
    public friendService: FriendService
  ) {}

  ngOnInit(): void {
    this.listFriends();

    this.friendService.friendsSubject.subscribe((friends) => {
      this.friends = friends;
    });
  }

  search() {
    console.log('Searching');
  }

  selectSuggestion(suggestion: string) {}

  onSearchInputChange() {}

  async listFriends() {
    const userId = this.authService.getUserIdFromToken();
    const response = this.friendService.fetchFriends(userId);

    this.friendService.fetchFriends(userId).subscribe(
      (friends) => {
        // Update the friendsSubject with fetched friends
        this.friends = friends;
      },
      (error) => {
        console.error('Error fetching friends information:', error);
      }
    );
  }
}
