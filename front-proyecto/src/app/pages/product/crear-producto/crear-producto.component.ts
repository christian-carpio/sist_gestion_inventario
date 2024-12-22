import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {InsumoDetalleDTO} from "../../inventario/interfaces/insumoDTO";
import {InventarioService} from "../../inventario/service/inventario.service";
import {ProductoService} from "../service/producto.service";
import Swal from "sweetalert2";
import {ActivatedRoute, Router} from "@angular/router";
import {ProductoDTO} from "../interfaces/product";
import {AuthService} from "../../../auth/service/auth.service";

@Component({
  selector: 'app-crear-producto',
  templateUrl: './crear-producto.component.html',
  styleUrls: ['./crear-producto.component.scss']
})
export class CrearProductoComponent implements OnInit {
  productoForm!: FormGroup;
  insumos: InsumoDetalleDTO[] = [];
  updateFlag = false;
  productoUpdate!: ProductoDTO;
  idProductUpdate!: number;

  constructor(private fb: FormBuilder,
              private router: Router,
              private insumoService: InventarioService,
              private productoService: ProductoService,
              private authService: AuthService,
              private activeRoute: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.onLoadProducto();
    this.productoForm = this.fb.group({
      nombre: new FormControl('', [Validators.required, Validators.max(10)]),
      precio: new FormControl(0, [Validators.required, Validators.pattern(/^[0-9]*$/)]),
      descripcion: new FormControl('', [Validators.required, Validators.max(50)]),
    });
  }


  onSubmit(): void {
    if (this.productoForm.valid) {
      if (this.updateFlag) {
        this.updateProducto();
      } else {
        this.registerProducto();
      }
    }
  }

  updateProducto() {
    const request = this.productoForm.value as ProductoDTO;
    request.nombreCreador = this.authService.getUser()!.sub;
    request.id = this.idProductUpdate.toString();
    request.requerimientoDTOSet = [];
    this.productoService.updateProduct(this.idProductUpdate, request).subscribe({ //Llamada AJAX
      next: (data) => {
        Swal.fire(
          'Actualizacion Completo',
          'Se ha actualizado  el producto con exito',
          'success'
        );
        this.router.navigateByUrl('/home');

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


  registerProducto() {
    const request = this.productoForm.value as ProductoDTO;
    request.nombreCreador = this.authService.getUser()!.sub;
    request.requerimientoDTOSet = [];
    this.productoService.registrar(request).subscribe({ //Llamada AJAX
      next: (data) => {
        Swal.fire(
          'Registro Completo',
          'Se ha registro el producto con exito',
          'success'
        );
        this.router.navigateByUrl('/home');

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


  onLoadProducto() {
    this.activeRoute.params
      .subscribe({  //Llamada AJAX
        next: ({id}) => {
          if (id) {
            this.productoService.getOne(id).subscribe({ //Llamada AJAX
              next: (product) => {
                this.productoUpdate = product;
                this.idProductUpdate = Number(product.id);
                this.updateFlag = true;
                this.actualizarForm();
              },
              error: (err) => {
                Swal.fire(
                  'Ha ocurrido un error',
                  err.error.message,
                  'error'
                );
                this.router.navigateByUrl("/home").then();
              }
            })
          }
        }
      });
  }


  private actualizarForm() {
    this.productoForm = this.fb.group({
      nombre: new FormControl(this.productoUpdate.nombre, [Validators.required, Validators.max(10)]),
      precio: new FormControl(this.productoUpdate.precio, [Validators.required, Validators.pattern(/^[0-9]*$/)]),
      descripcion: new FormControl(this.productoUpdate.descripcion, [Validators.required, Validators.max(50)]),
    });
  }

}
