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
  friends$: Observable<any[]> | undefined;

  constructor(
    private authService: AuthService,
    public friendService: FriendService
  ) {}

  ngOnInit(): void {
    this.listFriends();
  }

  search() {
    console.log('Searching');
  }

  selectSuggestion(suggestion: string) {}

  onSearchInputChange() {}

  async listFriends() {
    const userId = this.authService.getUserIdFromToken();
    if (userId) {
      this.friends$ = this.friendService.friends$(userId);
    }
  }
}
