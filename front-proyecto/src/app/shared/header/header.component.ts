import {Component, ElementRef, Renderer2, ViewChild} from '@angular/core';
import {AuthService} from "../../auth/service/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  username!: string;
  mobileMenuOpen = false;

  @ViewChild('navbar') navbar!: ElementRef;

  constructor(private authService: AuthService) {
  }

  logOut() {
    this.authService.logOut();
  }

  isLogged() {
    const user = this.authService.getUser();
    if (user) {
      this.username = user.sub;
      return true;
    }
    return false;

  }

  hasRole(role: string): boolean {
    const user = this.authService.getUser();
    const isAuthorized = user?.roles.includes(role);
    if (!isAuthorized) {
      return false
    }
    return true;
  }

  toggleMobileMenu() {
    this.mobileMenuOpen = !this.mobileMenuOpen;
  }

}
