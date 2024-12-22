import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {switchMap} from "rxjs";
import {InsumoDetalleDTO} from "../../interfaces/insumoDTO";
import {InventarioService} from "../../service/inventario.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-tipo-insumo-detail',
  templateUrl: './tipo-insumo-detail.component.html',
  styleUrls: ['./tipo-insumo-detail.component.scss']
})
export class InsumoDetailComponent implements OnInit {

  tipoInsumo!: InsumoDetalleDTO;

  constructor(
    private activeRoute: ActivatedRoute,
    private inventarioService: InventarioService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.activeRoute.params
      .pipe(switchMap(({id}) => this.inventarioService.getOneTipoInsumo(id)))
      .subscribe((product) => (this.tipoInsumo = product)); //Llamada AJAX
  }

  delete(id: number) {
    this.inventarioService.deleteInsumo(Number(id))
      .subscribe({  //Llamada AJAX
        next: (data) => {
          Swal.fire(
            'Registro Completo',
            'Se ha eliminado el insumo con exito',
            'success'
          );
          this.reloadProduct();
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

  reloadProduct(): void {
    this.inventarioService.getOneTipoInsumo(Number(this.tipoInsumo.id)).subscribe({ //Llamada AJAX
      next: (data) => {
        this.tipoInsumo = data;
      }
    })
  }


}
