import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ImageService } from '../../services/ImageService';
import { PostService } from '../../services/PostService';
import { AuthService } from '../../services/AuthService';
import { MatDialogRef } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css'],
})
export class NewPostComponent implements OnInit {
  private routeSubscription?: Subscription;
  pictureUrl = '';
  postType = 'Text';
  currentUrl: string = '';
  caption: string = '';
  selectedUserPic: File | null = null;
  @ViewChild('fileInput', { static: false }) fileInput?: ElementRef;

  constructor(
    private imageService: ImageService,
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    public dialogRef: MatDialogRef<NewPostComponent>
  ) {}

  ngOnInit(): void {
    // Get the profile's handle from the route parameters
    this.routeSubscription = this.route.params.subscribe((params) => {
      //this.handle = params['handle'];
      this.currentUrl = this.router.routerState.snapshot.url;
      //console.log(currentUrl);
      // Fetch profile information from the backend
    });
  }

  ngOnDestroy() {
    this.routeSubscription?.unsubscribe();
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];

    if (file) {
      // read the selected file and convert it into a data URL
      this.selectedUserPic = file;
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.pictureUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  async onSubmit() {
    // Handle submit action
    try {
      if (this.selectedUserPic) {
        this.postType = 'Image';
        const response = await this.imageService
          .uploadImage(this.selectedUserPic, false)
          .toPromise();
        console.log(response.userPic);
        this.pictureUrl = response.userPic;
      }
      const myId = this.authService.getUserIdFromToken();
      console.log(this.selectedUserPic?.name);
      console.log(this.caption);

      const response = this.postService.postPost(
        myId,
        this.postType,
        this.caption,
        this.pictureUrl
      );
      if (this.selectedUserPic) {
        const picLocation = this.pictureUrl; // Extract the userPic URL
        const userPicUrl = await this.imageService.getImage(picLocation);
        console.log(userPicUrl);
      }

      var post;
      await response
        .then((l) => {
          post = l;
        })
        .catch((error) => {
          console.error(error);
        });

      const user = this.authService.currentUserSubject.getValue();
      const newPost = [
        post,
        user?.userPic,
        user?.handle,
        user?.firstName,
        user?.lastName,
        0,
      ];

      // If on the user's own profile page or no ha
      console.log(this.currentUrl);
      console.log(this.authService.getHandle());
      if (
        this.currentUrl.includes(this.authService.getHandle()) ||
        this.currentUrl == '/home'
      ) {
        const currentPosts = this.postService.postsSubject.getValue();
        const updatedPosts = [newPost, ...currentPosts];
        this.postService.postsSubject.next(updatedPosts);
      }
      // const currentPosts = this.postService.postsSubject.getValue();
      // const updatedPosts = [newPost, ...currentPosts];
      // this.postService.postsSubject.next(updatedPosts);

      this.closeDialog();
    } catch (error) {
      // Handle error if the backend update fails
      console.error('Error Creating new post:', error);
    }
  }

  onCancel() {
    // Handle cancel action
    this.closeDialog();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
