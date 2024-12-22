import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../auth/service/auth.service";
import Swal from "sweetalert2";
import {InsumoDetalleDTO} from "../../interfaces/insumoDTO";
import {InventarioService} from "../../service/inventario.service";

@Component({
  selector: 'app-insumo',
  templateUrl: './insumo.component.html',
  styleUrls: ['./insumo.component.scss']
})
export class InsumoComponent implements OnInit {
  insumos: InsumoDetalleDTO[] = [];

  constructor(private authService: AuthService, private inventarioServicio: InventarioService) {
  }

  ngOnInit(): void {
    this.inventarioServicio.getAllTipoInsumo().subscribe({  //Llamada AJAX
      next: (data) => {
        this.insumos = data;
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
