import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';

const authRoutes: Routes = [
  {path: "login", title: "Login", component: LoginComponent},
    {path: "registration", title: "Registration", component: RegistrationComponent},
];

@NgModule({
  imports: [
    RouterModule.forChild(authRoutes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
