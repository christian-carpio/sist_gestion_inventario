import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import Swal from "sweetalert2";
import {Client, UpdateUser} from "../../interfaces/Client";
import {ClientService} from "../../service/client.service";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import * as timers from "timers";
import {timeout} from "rxjs";

@Component({
  selector: 'app-update-info-user',
  templateUrl: './update-info-user.component.html',
  styleUrls: ['./update-info-user.component.scss']
})
export class UpdateInfoUserComponent implements OnInit {
  formGroup!: FormGroup;
  clientUpdate: UpdateUser = {name: '', password: '', image: ''};
  client!: Client;
  username!: string;

  loading = true;

  constructor(private router: Router, private clientService: ClientService, private authService: AuthService) {
    this.formGroup = new FormGroup(
      {
        name: new FormControl('', [Validators.required, Validators.max(20)]),
        pictureBase64: new FormControl('', [Validators.required]),
        password: new FormControl('', []),
      });


  }

  ngOnInit() {

    this.username = this.authService.getUser()!.sub;

    this.clientService.getInfo(this.username)
      .subscribe({  // Llamada AJAX
        next: (user) => {
          this.client = user;
          console.log(user);
          this.loading = false;
          this.formGroup.controls['name'].setValue(this.client.name);
          this.formGroup.controls['pictureBase64'].setValue(this.client.urlImage);
        },
        error: (err) => {
          this.loading = false;
          Swal.fire(
            'Error',
            err.error.message,
            'error'
          )
          this.router.navigateByUrl('/login').then();
        }
      });
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file && file.type.match(/image.*/)) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.formGroup.patchValue({pictureBase64: reader.result});
      };
    } else {
      Swal.fire(
        'Archivo no valido',
        'Se debe subir una imagen',
        'warning'
      );
    }
  }

  onSubmit() {
    const {name, pictureBase64, password} = this.formGroup.value;
    const image = pictureBase64.split(',')[1];

    this.clientUpdate = {password, name, image};

    this.clientService.putInfo(this.clientUpdate, this.username)
      .subscribe({
        next: () => {
          this.router.navigateByUrl('/info-user').then();
        }, error: err => {
          Swal.fire(
            'Archivo no valido',
            err.error.message,
            'error'
          );
          // this.router.navigateByUrl('/login').then();
        }
      });
  }
}
