import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-sidemenu',
  templateUrl: './sidemenu.component.html',
  styleUrls: ['./sidemenu.component.css'],
})
export class SidemenuComponent {
  constructor(private authService: AuthService) {}

  getUsername(): string | null {
    return this.authService.getUsername();
  }
}
