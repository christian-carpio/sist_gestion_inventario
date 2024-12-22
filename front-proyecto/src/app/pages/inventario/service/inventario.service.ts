import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {urlMsvcInventario} from "../../../constants/Constants";
import {InsumoDTO, InsumoDetalleDTO} from "../interfaces/insumoDTO";

@Injectable({
  providedIn: 'root'
})
export class InventarioService {

  apiUrl = `${urlMsvcInventario}`;

  constructor(private http: HttpClient) {
  }

  public registerTipoInsumo(register: InsumoDetalleDTO): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/insumo-detalle/registrarInsumoDetalle`, register);
  }

  public updateTipoInsumo(id: number, request: InsumoDetalleDTO): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/insumo-detalle/actualizarInsumoDetalle/${id}`, request);
  }

  public getAllTipoInsumo(): Observable<InsumoDetalleDTO[]> {
    return this.http.get<InsumoDetalleDTO[]>(`${this.apiUrl}/insumo-detalle/listarInsumoDetalles`);
  }

  public getOneTipoInsumo(id: number): Observable<InsumoDetalleDTO> {
    return this.http.get<InsumoDetalleDTO>(`${this.apiUrl}/insumo-detalle/traerInsumoDetalle/${id}`);
  }

  public deleteTipoInsumo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/insumo-detalle/eliminarInsumoDetalle/${id}`);
  }


  // para insumos

  public updateInsumo(id: number, request: InsumoDTO): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/insumo/actualizarInsumo/${id}`, request);
  }

  public getAllInsumo(): Observable<InsumoDTO[]> {
    return this.http.get<InsumoDTO[]>(`${this.apiUrl}/insumo/listarInsumos`);
  }

  public getOneInsumo(id: number): Observable<InsumoDTO> {
    return this.http.get<InsumoDTO>(`${this.apiUrl}/insumo/traerInsumo/${id}`);
  }

  public registerInsumo(register: InsumoDTO): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/insumo/registrarInsumo`, register);
  }

  public deleteInsumo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/insumo/eliminarInsumo/${id}`);
  }

}
