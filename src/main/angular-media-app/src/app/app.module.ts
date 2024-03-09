import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AuthModule } from './auth/auth.module';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { Router } from '@angular/router';
import { environment } from './environments/environment.prod';


@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    AuthModule,
    AppRoutingModule
  ],
  bootstrap: [ AppComponent ],
  providers: [
    ...environment.providers
  ]
})
export class AppModule { 
  constructor(router: Router){

  }
}
