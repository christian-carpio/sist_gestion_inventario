import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {InventarioService} from "../../service/inventario.service";
import Swal from "sweetalert2";
import {ActivatedRoute, Router} from "@angular/router";
import {InsumoDetalleDTO} from "../../interfaces/insumoDTO";

@Component({
  selector: 'app-crear-insumo',
  templateUrl: './crear-insumo.component.html',
  styleUrls: ['./crear-insumo.component.scss']
})
export class CrearInsumoComponent implements OnInit {
  tipoInsumoForm!: FormGroup;
  updateTipoInsumo!: InsumoDetalleDTO;
  updateFlag = false;

  idTipoInsumo!: number;
  constructor(private fb: FormBuilder,
              private inventarioService: InventarioService,
              private router: Router,
              private activeRoute: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.initByUpdate();
    this.tipoInsumoForm = this.fb.group({
      nombre: new FormControl('', [Validators.required, Validators.max(10)]),
      descripcion: new FormControl('', [Validators.required, Validators.max(50)])
    });
  }

  onSubmit() {
    if (this.tipoInsumoForm.valid) {

      if (this.updateFlag) {
        this.onUpdate();
        return;
      }


      const tipoInsumoDTO = this.tipoInsumoForm.value as InsumoDetalleDTO;
      this.inventarioService.registerTipoInsumo(tipoInsumoDTO)
        .subscribe({  //Llamada AJAX
          next: (data) => {
            Swal.fire(
              'Registro Completo',
              'Se ha guardo el insumo con exito',
              'success'
            );
            this.router.navigateByUrl('/insumo');

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


  private initByUpdate() {
    this.activeRoute.params
      .subscribe({  //Llamada AJAX
        next: ({id}) => {
          if (id) {
            this.inventarioService.getOneTipoInsumo(id).subscribe({ //Llamada AJAX
              next: (inventario) => {
                this.updateTipoInsumo = inventario
                this.idTipoInsumo  = inventario.id;
                this.actualizarForm();
                this.updateFlag = true;
              },
              error: (err) => {
                Swal.fire(
                  'Ha ocurrido un error',
                  err.error.message,
                  'error'
                );
                this.router.navigateByUrl("/inventario").then();
              }
            })
          }
        }
      });
  }

  private actualizarForm() {
    this.tipoInsumoForm = this.fb.group({
      nombre: new FormControl(this.updateTipoInsumo.nombre, [Validators.required, Validators.max(10)]),
      descripcion: new FormControl(this.updateTipoInsumo.descripcion, [Validators.required, Validators.max(50)]),
    });
  }


  private onUpdate() {
    const tipoInsumoDTO = this.tipoInsumoForm.value as InsumoDetalleDTO;
    this.inventarioService.updateTipoInsumo(this.idTipoInsumo, tipoInsumoDTO)
      .subscribe({  //Llamada AJAX
        next: (data) => {
          Swal.fire(
            'Registro Completo',
            'Se ha actualizado el tipoinsumo con exito',
            'success'
          );
          this.router.navigateByUrl("/tipo-inventario").then();
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
