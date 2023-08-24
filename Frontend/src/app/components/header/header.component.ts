import { Component } from '@angular/core';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  constructor(private authService: AuthService) {}

  onLogoutClick() {
    this.authService.logout();
  }
}
