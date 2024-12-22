import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProductoService} from "../service/producto.service";
import {switchMap} from "rxjs";
import {ProductoDTO} from "../interfaces/product";
import {InsumoDetalleDTO} from "../../inventario/interfaces/insumoDTO";
import Swal from "sweetalert2";
import {InventarioService} from "../../inventario/service/inventario.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {RequerimientoDTO} from "../../../interfaces/requerimiento";

@Component({
  selector: 'app-detail-product',
  templateUrl: './detail-product.component.html',
  styleUrls: ['./detail-product.component.scss']
})
export class DetailProductComponent implements OnInit {

  product!: ProductoDTO;
  insumos: InsumoDetalleDTO[] = [];

  constructor(
    private activeRoute: ActivatedRoute,
    private insumoService: InventarioService,
    private productService: ProductoService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.requerimientoForm = this.fb.group({
      cantidad: new FormControl(0, [Validators.required, Validators.pattern(/^[0-9]*$/)]),
      insumoId: new FormControl(null, [Validators.required, Validators.pattern(/^[0-9]*$/)]),
    });
  }

  requerimientoForm: FormGroup;


  ngOnInit(): void {
    this.activeRoute.params
      .pipe(switchMap(({id}) => this.productService.getOne(id)))
      .subscribe((product) => (this.product = product));  //Llamada AJAX
    this.onLoadInsumos();
  }


  onLoadInsumos() {
    this.insumoService.getAllTipoInsumo().subscribe({ //Llamada AJAX
      next: (data) => {
        this.insumos = data;
      },
      error: (err) => {
        Swal.fire(
          'Ha ocurrido un error cargar los insumos',
          err.error.message,
          'error'
        );
        this.router.navigateByUrl('/home').then();
      }
    });
  }

  onAgregarRequerimiento() {
    const request = this.requerimientoForm.value as RequerimientoDTO;
    request.productoId = Number(this.product.id);
    this.productService.registrarRequerimiento(request)
      .subscribe({  //Llamada AJAX
        next: (data) => {
          Swal.fire(
            'Exito',
            'Se guardo con existo el requerimiento',
            'success'
          );
          this.reloadProduct();

        },
        error: (err) => {
          Swal.fire(
            'Ha ocurrido un error al guardar el requerimiento',
            err.error.message,
            'error'
          );

        },
        complete: () => {
          this.requerimientoForm.reset();
        }
      });

  }

  getNombreInsumo(id: number): string{
    if(this.insumos && this.insumos.length>0){
      const tipoInsumo = this.insumos.find(value => value.id === id);
      return tipoInsumo!.nombre;
    }else{
      return "cargando..."
    }
  }
  eliminarRequerimiento(id: number) {
    this.productService
      .deleteRequerimientoById(id)
      .subscribe( //Llamada AJAX
        {
          next: () => {
            Swal.fire(
              'Exito',
              'Se ha eliminado con existo el requerimiento',
              'success'
            );
          },
          error: (err) => {
            Swal.fire(
              'Ha ocurrido un error al guardar el requerimiento',
              err.error.message,
              'error'
            );

          },
          complete: () => {
            this.reloadProduct();
          }
        }
      )
  }

  reloadProduct(): void {
    this.productService.getOne(Number(this.product.id)).subscribe({ //Llamada AJAX
      next: (data) => {
        this.product = data;
      }
    })
  }
}
