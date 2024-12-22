import {Component, OnInit} from '@angular/core';
import {Client} from "../../interfaces/Client";
import {ClientService} from "../../service/client.service";
import {AuthService} from "../../service/auth.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-info-user',
  templateUrl: './info-user.component.html',
  styleUrls: ['./info-user.component.scss']
})
export class InfoUserComponent implements OnInit {

  client!: Client;

  ngOnInit(): void {
    const usuario = this.authService.getUser()!.sub;
    
    this.clientService.getInfo(usuario)
      .subscribe({  // Llamada AJAX
        next: (user) => {
          this.client = user;
        },
        error: (err) => {
          Swal.fire(
            'Error',
            err.error.message,
            'error'
          )
        }
      });
  }

  constructor(private clientService: ClientService, private authService: AuthService) {
  }


}
