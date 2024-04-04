import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';

const authRoutes: Routes = [
  {path: "auth/login", title: "Login", component: LoginComponent},
  {path: "auth/registration", title: "Registration", component: RegistrationComponent},
  {path: '', redirectTo: '/auth/login', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forChild(authRoutes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
