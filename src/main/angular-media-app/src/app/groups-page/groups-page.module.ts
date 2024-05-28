import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GroupsPageRoutingModule } from './groups-page-routing.module';
import { GroupsPageComponent } from './groups-page.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    GroupsPageComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    GroupsPageRoutingModule
  ]
})
export class GroupsPageModule{

}
