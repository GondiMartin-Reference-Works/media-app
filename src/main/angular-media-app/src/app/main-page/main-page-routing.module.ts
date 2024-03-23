import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './main-page.component';
import { authGuard } from '../auth/guards/auth.guard';

const routes: Routes = [
  { 
    path: '', 
    component: MainPageComponent, 
    canActivate: [authGuard] 
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainPageRoutingModule { }
