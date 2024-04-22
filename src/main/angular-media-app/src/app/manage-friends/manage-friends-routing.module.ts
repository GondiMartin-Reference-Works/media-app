import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManageFriendsComponent } from './manage-friends.component';

const routes: Routes = [{ path: '', title: "My friends", component: ManageFriendsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManageFriendsRoutingModule { }
