import { Component, Input } from '@angular/core';
import { AuthService } from '../../services/AuthService';
import { ImageService } from '../../services/ImageService';

@Component({
  selector: 'app-suggestion-item',
  templateUrl: './suggestion-item.component.html',
  styleUrls: ['./suggestion-item.component.css'],
})
export class SuggestionItemComponent {
  @Input() suggestion: any;
  postImageURL = '';

  constructor(
    public imageService: ImageService,
    public authService: AuthService
  ) {}

  async ngOnInit() {
    const userPicLocation = this.suggestion.userPic;
    this.postImageURL = await this.imageService.getImage(userPicLocation);
  }
}
