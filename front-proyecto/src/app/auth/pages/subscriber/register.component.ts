import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {Register} from "../../interfaces/Register";
import Swal from "sweetalert2";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  formGroup!: FormGroup;

  public constructor(private router: Router, private authService: AuthService) {
    this.formGroup = new FormGroup(
      {
        username: new FormControl('', [Validators.required, Validators.max(10)]),
        name: new FormControl('', [Validators.required, Validators.max(10)]),
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', [Validators.required]),
      }
    )
  }

  onRegister() {
    const register = this.formGroup.value as Register;
    register.roles = [];

    this.authService.register(register)
      .subscribe({  // Llamada AJAX
        next: (data) => {
          Swal.fire(
            'Registro Completo',
            'Se ha registrado con exito',
            'success'
          );
          this.router.navigateByUrl('/login');
        },
        error: (err) => {
          Swal.fire(
            'Ha ocurrido un error',
            err.error.message,
            'error'
          );
        }
      });

  }


}
