import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FriendRequestRoutingModule } from './friend-request-routing.module';
import { FriendRequestComponent } from './friend-request.component';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    FriendRequestComponent 
  ],
  imports: [
    CommonModule,
    FriendRequestRoutingModule
  ]
})
export class FriendRequestModule { }
