import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Router, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
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
    LoginFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    RouterModule.forRoot([
      {path:"home", component:HomeComponent},
      // Automatically direct to homepage if path value is empty
      {path:'',redirectTo:'/home', pathMatch:'full'}
    ]),
    HttpClientModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
