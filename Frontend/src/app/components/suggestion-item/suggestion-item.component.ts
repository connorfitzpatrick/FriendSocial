import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-suggestion-item',
  templateUrl: './suggestion-item.component.html',
  styleUrls: ['./suggestion-item.component.css'],
})
export class SuggestionItemComponent {
  @Input() suggestion: any;
}
