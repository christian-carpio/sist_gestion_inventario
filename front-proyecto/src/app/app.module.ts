import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {HomeComponent} from './pages/product/home/home.component';
import {SharedModule} from "./shared/shared.module";
import {ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {JwtInterceptorInterceptor} from "./auth/service/jwt-interceptor.interceptor";
import {AppComponent} from "./app.component";
import {DetailProductComponent} from './pages/product/detail-product/detail-product.component';
import {CrearInsumoDetalleComponent} from './pages/inventario/pages/crear-inventario/crear-insumo-detalle.component';
import {InsumoComponent} from './pages/inventario/pages/inventarios/insumo.component';
import {CrearProductoComponent} from './pages/product/crear-producto/crear-producto.component';
import {
  CrearInsumoComponent
} from "./pages/inventario/pages/crear-tipo-inventario/crear-insumo.component";
import {InsumoDetailComponent} from "./pages/inventario/pages/tipo-insumo-detail/tipo-insumo-detail.component";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    DetailProductComponent,
    CrearInsumoDetalleComponent,
    InsumoComponent,
    CrearProductoComponent,
    InsumoDetailComponent,
    CrearInsumoComponent,



  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
