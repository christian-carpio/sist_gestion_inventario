import {Component, OnInit} from '@angular/core';
import {ProductoService} from "../service/producto.service";
import Swal from "sweetalert2";
import {ProductoDTO} from "../interfaces/product";
import {AuthService} from "../../../auth/service/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  products: ProductoDTO[] = [];

  constructor(private authService: AuthService, private productService: ProductoService) {
  }

  ngOnInit(): void {
    this.productService.getAll().subscribe({  //Llamada AJAX
      next: (data) => {
        this.products = data;
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

  hasRole(role: string): boolean {
    const user = this.authService.getUser();
    const isAuthorized = user?.roles.includes(role);
    if (!isAuthorized) {
      return true
    }
    return true;
  }

  delete(id: string) {
    this.productService.deleteById(Number(id))
      .subscribe({  //Llamada AJAX
        next: (data) => {
          Swal.fire(
            'Registro Completo',
            'Se ha eliminado el producto con exito',
            'success'
          );
          window.location.reload();
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
