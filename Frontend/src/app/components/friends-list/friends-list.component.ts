import {
  Component,
  OnInit,
  ElementRef,
  HostListener,
  ViewChild,
} from '@angular/core';
import { AuthService } from '../../services/AuthService';
import { FriendService } from '../../services/FriendService';

@Component({
  selector: 'app-friends-list',
  templateUrl: './friends-list.component.html',
  styleUrls: ['./friends-list.component.css'],
})
export class FriendsListComponent implements OnInit {
  userInput: string = '';
  searchSuggestions: any[] = []; // Change the type to an array
  friendsList: any[] = [];
  showSuggestions: boolean = false;
  @ViewChild('searchGroup') searchGroup!: ElementRef;

  constructor(
    private authService: AuthService,
    public friendService: FriendService,
    private elementRef: ElementRef
  ) {}

  ngOnInit() {
    const userId = this.authService.getUserIdFromToken();
    this.friendService.fetchFriends(userId); // No need to await here
    this.friendService.friends$.subscribe((friends) => {
      this.friendsList = friends;
      console.log(this.friendsList);
    });
  }

  onSearchInputChange() {
    this.showSuggestions = true;
    console.log(this.userInput);
    this.friendService
      .searchFriends(this.userInput)
      .subscribe((suggestions) => {
        this.searchSuggestions = suggestions;
      });
    console.log(this.searchSuggestions);
  }

  @HostListener('document:click', ['$event'])
  onClick(event: Event) {
    // Check if the click event target is outside the suggestions box
    const clickedInsideSearchGroup = this.searchGroup.nativeElement.contains(
      event.target
    );
    if (!clickedInsideSearchGroup) {
      this.showSuggestions = false;
    } else if (!this.showSuggestions && clickedInsideSearchGroup) {
      this.showSuggestions = true;
    }
  }
}
