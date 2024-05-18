import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserPageRoutingModule } from './user-page-routing.module';
import { UserPageComponent } from './user-page.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    UserPageComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    UserPageRoutingModule
  ]
})
export class UserPageModule { }
