import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GroupsPageComponent } from './groups-page.component';
import { authGuard } from '../auth/guards/auth.guard';

const routes: Routes = [
  { path: '',
    title: "My Groups",
    component: GroupsPageComponent,
    canActivate: [authGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GroupsPageRoutingModule { }
