import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from "./pages/login/login.component";
import {AuthRoutingModule} from "./auth-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import {RegisterComponent} from "./pages/subscriber/register.component";
import { InfoUserComponent } from './pages/info-user/info-user.component';
import { UpdateInfoUserComponent } from './pages/update-info-user/update-info-user.component';


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    InfoUserComponent,
    UpdateInfoUserComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
  ]
})
export class AuthModule { }
