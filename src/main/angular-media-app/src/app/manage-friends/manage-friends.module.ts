import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManageFriendsRoutingModule } from './manage-friends-routing.module';
import { ManageFriendsComponent } from './manage-friends.component';


@NgModule({
  declarations: [
    ManageFriendsComponent
  ],
  imports: [
    CommonModule,
    ManageFriendsRoutingModule
  ]
})
export class ManageFriendsModule { }
