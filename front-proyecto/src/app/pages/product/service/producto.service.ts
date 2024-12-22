import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {urlMsvcProducto} from "../../../constants/Constants";
import {ProductoDTO} from "../interfaces/product";
import {RequerimientoDTO} from "../../../interfaces/requerimiento";

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  apiUrl = `${urlMsvcProducto}`;

  constructor(private http: HttpClient) {
  }

  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/eliminarProducto/${id}`);
  }


  public getAll(): Observable<ProductoDTO[]> {
    return this.http.get<ProductoDTO[]>(`${this.apiUrl}/listarProductos`);
  }

  public getOne(id: number): Observable<ProductoDTO> {
    return this.http.get<ProductoDTO>(`${this.apiUrl}/traerProducto/${id}`);
  }

  public registrar(request: ProductoDTO): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/registrarProducto`, request);
  }

  public updateProduct(id: number, request: ProductoDTO): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/actualizarProducto/${id}`, request);
    // return this.http.put<any>(`http://localhost:64666/actualizarProducto/${id}`, request);
  }

  ///// requerimiento
  public deleteRequerimientoById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/eliminarRequerimiento/${id}`);
  }

  public registrarRequerimiento(request: RequerimientoDTO): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/registrarRequerimiento`, request);
  }

}
