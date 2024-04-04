import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AuthModule } from './auth/auth.module';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { MainPageModule } from './main-page/main-page.module';
import { FriendRequestComponent } from './friend-request/friend-request.component';
import { FriendRequestModule } from './friend-request/friend-request.module';
import { ManageFriendsComponent } from './manage-friends/manage-friends.component';
import { ManageFriendsModule } from './manage-friends/manage-friends.module';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    CommonModule,
    FormsModule,
    AuthModule,
    MainPageModule,
    AppRoutingModule,
    FriendRequestModule,
    ManageFriendsModule
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule {
  constructor(router: Router){

  }
}
