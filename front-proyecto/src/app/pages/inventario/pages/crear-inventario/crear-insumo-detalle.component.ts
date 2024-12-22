import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {InventarioService} from "../../service/inventario.service";
import Swal from "sweetalert2";
import {ActivatedRoute, Router} from "@angular/router";
import {InsumoDTO} from "../../interfaces/insumoDTO";

@Component({
  selector: 'app-crear-insumo-detalle',
  templateUrl: './crear-insumo-detalle.component.html',
  styleUrls: ['./crear-insumo-detalle.component.scss']
})
export class CrearInsumoDetalleComponent implements OnInit {
  tipoInsumoForm!: FormGroup;
  updateInsumo!: InsumoDTO;
  updateFlag = false;
  idInsumo!: number;
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
      cantidad: new FormControl(0, [Validators.required, Validators.pattern(/^[0-9]*$/)]),
      fechaFabricacion: new FormControl('', [Validators.required, Validators.pattern('^\\d{4}-\\d{2}-\\d{2}$')]),
      fechaExpiracion: new FormControl('', [Validators.required, Validators.pattern('^\\d{4}-\\d{2}-\\d{2}$')]),
    });
  }

  onSubmit() {
    if (this.tipoInsumoForm.valid) {

      if (this.updateFlag) {
        this.onUpdate();
        return;
      }


      const insumoDto = this.tipoInsumoForm.value as InsumoDTO;
      insumoDto.insumoIdpk = this.idTipoInsumo;

      this.inventarioService.registerInsumo(insumoDto)
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
        next: ({idTipoInsumo, idInsumo}) => {
          if (idTipoInsumo) {
            this.inventarioService.getOneTipoInsumo(idTipoInsumo).subscribe({ //Llamada AJAX
              next: (tipoInsumo) => {
                this.idTipoInsumo = tipoInsumo.id;
              },
              error: (err) => {
                Swal.fire(
                  'Ha ocurrido un error',
                  err.error.message,
                  'error'
                );
                this.router.navigateByUrl("/inventario").then();
              }
            });
          }
          if (idInsumo) {
            this.inventarioService.getOneInsumo(idInsumo).subscribe({ //Llamada AJAX
              next: (insumo) => {
                this.updateInsumo = insumo
                this.idInsumo = insumo.id;
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
      cantidad: new FormControl(this.updateInsumo.cantidad, [Validators.required, Validators.pattern(/^[0-9]*$/)]),
      fechaFabricacion: new FormControl(this.updateInsumo.fechaExpiracion, [Validators.required, Validators.pattern('^\\d{4}-\\d{2}-\\d{2}$')]),
      fechaExpiracion: new FormControl(this.updateInsumo.fechaExpiracion, [Validators.required, Validators.pattern('^\\d{4}-\\d{2}-\\d{2}$')]),
    });
  }


  private onUpdate() {
    const insumoDto = this.tipoInsumoForm.value as InsumoDTO;
    insumoDto.insumoIdpk = this.updateInsumo.insumoIdpk;
    this.inventarioService.updateInsumo(this.idInsumo, insumoDto)
      .subscribe({  //Llamada AJAX
        next: (data) => {
          Swal.fire(
            'Actualizacion Completo',
            'Se ha actualizado el insumo con exito',
            'success'
          );
          this.router.navigateByUrl("/inventario").then();

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
