import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ContactPageRoutingModule } from './contact-page-routing.module';
import { ContactPageComponent } from './contact-page.component';
import { FormsModule } from '@angular/forms'; // import FormsModule



@NgModule({
  declarations: [
    ContactPageComponent
  ],
  imports: [
    CommonModule,
    ContactPageRoutingModule,
    FormsModule
  ]
})
export class ContactPageModule { }
