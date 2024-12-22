import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {Login} from "../../interfaces/Login";
import Swal from "sweetalert2";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  formGroup!: FormGroup;

  public constructor(private router: Router, private authService: AuthService) {
    this.formGroup = new FormGroup(
      {
        username: new FormControl('', [Validators.required, Validators.max(10)]),
        password: new FormControl('', [Validators.required]),
      }
    )
  }

  ngOnInit(): void {
    if (this.authService.getUser()) {
      this.router.navigateByUrl('home').then();
    }
  }

  onLogin() {

    const login = this.formGroup.value as Login;

    this.authService.login(login)
      .subscribe({  // Llamada AJAX
        next: (data) => {
          this.router.navigateByUrl('home').then();
        },
        error: (err) => {
          Swal.fire(
            'Error',
            'Credenciales incorrectas',
            'error'
          )

        }
      });

  }

}
