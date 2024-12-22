import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/subscriber/register.component";

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    title: 'Login',
    pathMatch: 'full'
  },
  {
    path:'register',
    component: RegisterComponent,
    title: 'Register',
    pathMatch: 'full'
  }
 ];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
