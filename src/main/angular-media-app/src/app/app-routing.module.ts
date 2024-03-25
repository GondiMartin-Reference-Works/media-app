import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PreloadAllModules } from '@angular/router';
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
    path: '**',
    title: "Page Not Found",
    component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(
    routes,
    {
      preloadingStrategy: PreloadAllModules
    })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
