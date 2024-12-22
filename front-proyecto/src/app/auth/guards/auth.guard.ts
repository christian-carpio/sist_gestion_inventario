import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from "../service/auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const token = this.authService.getToken();
    if(!token){
      this.router.navigateByUrl('/');
      return false;
    }
    const {role} = route.data;
    const user = this.authService.getUser();
    const isAuthorized = user?.roles.includes(role);


    if (!isAuthorized) {
      this.router.navigateByUrl('/');
      return false
    }
    return true;
  }

}
