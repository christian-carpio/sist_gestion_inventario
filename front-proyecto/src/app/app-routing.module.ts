import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/product/home/home.component";
import {AuthGuard} from "./auth/guards/auth.guard";
import {DetailProductComponent} from "./pages/product/detail-product/detail-product.component";
import {CrearInsumoDetalleComponent} from "./pages/inventario/pages/crear-inventario/crear-insumo-detalle.component";
import {CrearProductoComponent} from "./pages/product/crear-producto/crear-producto.component";
import {InsumoDetailComponent} from "./pages/inventario/pages/tipo-insumo-detail/tipo-insumo-detail.component";
import {InsumoComponent} from "./pages/inventario/pages/inventarios/insumo.component";
import {CrearInsumoComponent} from "./pages/inventario/pages/crear-tipo-inventario/crear-insumo.component";
import {InfoUserComponent} from "./auth/pages/info-user/info-user.component";
import {UpdateInfoUserComponent} from "./auth/pages/update-info-user/update-info-user.component";

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'info-user',
    title: 'Informacion Usuario',
    component: InfoUserComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_CLIENTE',
    },

  },
  {
    path: 'update-info',
    title: 'Actualizar Cliente',
    component: UpdateInfoUserComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_CLIENTE',
    },

  },
  {
    path: 'home',
    title: 'Home',
    component: HomeComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_CLIENTE',
    },

  },
  {
    path: 'product-detail/:id',
    title: 'Detalle del producto',
    component: DetailProductComponent,
  },
  {
    path: 'register-product',
    title: 'Registrar del producto',
    component: CrearProductoComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_REGISTER_PRODUCT',
    },
  },
  {
    path: 'actualizar-product/:id',
    title: 'Actualizar producto',
    component: CrearProductoComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_REGISTER_PRODUCT',
    },
  },
  {
    path: 'inventario',
    title: 'Insumos',
    component: InsumoComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_REGISTER_PRODUCT',
    },
  },
  {
    path: 'inventario-detail/:id',
    title: 'Detalle del Inventario',
    component: InsumoDetailComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_REGISTER_PRODUCT',
    },
  },
  {
    path: 'register-insumo',
    title: 'Registrar del Insumo',
    component: CrearInsumoComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_REGISTER_PRODUCT',
    },
  },
  {
    path: 'register-insumo-detalle/:idTipoInsumo',
    title: 'Registrar del Detalle',
    component: CrearInsumoDetalleComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_REGISTER_PRODUCT',
    },
  },
  {
    path: 'actualizar-insumo/:idInsumo',
    title: 'Actualizar Insumo',
    component: CrearInsumoDetalleComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_REGISTER_PRODUCT',
    },
  },
  {
    path: 'inventario-update/:id',
    title: 'Actualizar Inventario',
    component: CrearInsumoComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_REGISTER_PRODUCT',
    },
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
