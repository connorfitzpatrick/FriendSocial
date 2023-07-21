import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { SidemenuComponent } from './components/sidemenu/sidemenu.component';
import { BlogPostComponent } from './components/blog-post/blog-post.component';
import { FeedComponent } from './components/feed/feed.component';
import { CommentRowComponent } from './components/comment-row/comment-row.component';
import { CommentDialogComponent } from './components/comment-dialog/comment-dialog.component';
import { LikesDialogComponent } from './components/likes-dialog/likes-dialog.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { TokenExpirationInterceptor } from './interceptors/token-expiration-interceptor.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    SidemenuComponent,
    BlogPostComponent,
    FeedComponent,
    CommentRowComponent,
    CommentDialogComponent,
    LikesDialogComponent,
    LoginFormComponent,
    ProfilePageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    RouterModule.forRoot([]),
    HttpClientModule,
  ],
  providers: [
    DatePipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenExpirationInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
