import { NgModule } from '@angular/core';
import { RouterModule, Routes, PreloadAllModules } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { authGuard } from './auth/guards/auth.guard';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(a => a.AuthModule)
  },
  {
    path: 'main',
    loadChildren: () => import('./main-page/main-page.module').then(m => m.MainPageModule),
    canMatch: [authGuard]
  },
  {
    path: '',
    redirectTo: '/main',
    pathMatch: 'full'},
  {
    path: 'friend-requests',
    loadChildren: () => import('./friend-request/friend-request.module').then(m => m.FriendRequestModule) ,
    canMatch: [authGuard]
  },
  { path: 'manage-friends',
    loadChildren: () => import('./manage-friends/manage-friends.module').then(m => m.ManageFriendsModule),
    canMatch: [authGuard]
  },
  { path: 'user-page/:userId',
    loadChildren: () => import('./user-page/user-page.module').then(m => m.UserPageModule),
    canMatch: [authGuard]
  },
  { path: 'groups',
    loadChildren: () => import('./groups-page/groups-page.module').then(m => m.GroupsPageModule),
    canMatch: [authGuard]
  },
  { path: 'contact-page/:userId', loadChildren: () => import('./contact-page/contact-page.module').then(m => m.ContactPageModule),
    canMatch: [authGuard]
   },
  {
    path: '**',
    title: "Page Not Found",
    component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(
    routes,
    {
      preloadingStrategy: PreloadAllModules,
      onSameUrlNavigation: 'reload',
      bindToComponentInputs: true
    })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
