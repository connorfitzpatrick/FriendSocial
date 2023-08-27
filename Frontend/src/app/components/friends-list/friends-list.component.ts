import {
  Component,
  OnInit,
  ElementRef,
  HostListener,
  ViewChild,
} from '@angular/core';
import { AuthService } from '../../services/AuthService';
import { FriendService } from '../../services/FriendService';
import { ProfileService } from '../../services/ProfileService';
import { User } from '../../models/profile.model';

@Component({
  selector: 'app-friends-list',
  templateUrl: './friends-list.component.html',
  styleUrls: ['./friends-list.component.css'],
})
export class FriendsListComponent implements OnInit {
  userInput: string = '';
  searchSuggestions: any[] = [];
  friendsList: any[] = [];
  showSuggestions: boolean = false;
  @ViewChild('searchGroup') searchGroup!: ElementRef;

  constructor(
    private authService: AuthService,
    private profileService: ProfileService,
    public friendService: FriendService,
    private elementRef: ElementRef
  ) {}

  ngOnInit() {
    const handle = this.authService.getHandle();
    // Fetch the user data and update the currentUserSubject
    this.profileService.fetchLoggedInUserData(handle).subscribe(
      (user: User) => {
        this.authService.currentUserSubject.next(user);
      },
      (error) => {
        console.error('Error fetching user data:', error);
      }
    );
    const userId = this.authService.getUserIdFromToken();
    this.friendService.fetchFriends(userId);
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
