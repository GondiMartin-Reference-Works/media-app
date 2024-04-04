import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FriendRequestRoutingModule } from './friend-request-routing.module';
import { FriendRequestComponent } from './friend-request.component';
import { RouterModule, Routes } from '@angular/router';

const authRoutes: Routes = [
  {path: "friend-request", title: "Friend requests", component: FriendRequestComponent}
]

@NgModule({
  declarations: [
    FriendRequestComponent 
  ],
  imports: [
    CommonModule,
    FriendRequestRoutingModule,
    [RouterModule.forChild(authRoutes)]
  ]
})
export class FriendRequestModule { }
