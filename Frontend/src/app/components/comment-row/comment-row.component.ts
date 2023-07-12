import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-comment-row',
  templateUrl: './comment-row.component.html',
  styleUrls: ['./comment-row.component.css']
})
export class CommentRowComponent {
  @Input() comments: any[] = [];
  showAllComments: boolean = false;
  threshold: number = 2; // Number of comments to display initially

  toggleComments(): void {
    this.showAllComments = !this.showAllComments;
  }
}

