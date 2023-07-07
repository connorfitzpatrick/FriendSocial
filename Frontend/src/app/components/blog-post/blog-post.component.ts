import { Component, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-blog-post',
  templateUrl: './blog-post.component.html',
  styleUrls: ['./blog-post.component.css']
})
export class BlogPostComponent {
  @Input() post: any;

  constructor(private http: HttpClient) {}

  /*
  ngOnInit() {
    this.getProfilePicture();
  }

  
  getProfilePicture() {
    const profilePictureUrl = `http://localhost:8080/api/v1/profiles/${this.post[0].profileId}/picture`;
    this.http.get(profilePictureUrl, { responseType: 'blob' }).subscribe(
      (data: any) => {
        const reader = new FileReader();
        reader.onloadend = () => {
          const base64data = reader.result as string;
          this.post.profilePicture = base64data;
        }
        reader.readAsDataURL(data);
      },
      (error) => {
        console.log('Error retrieving profile picture:', error);
      }
    );
  }
  */

}
