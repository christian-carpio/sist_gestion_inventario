import {RequerimientoDTO} from "../../../interfaces/requerimiento";

export interface ProductoDTO {
  id: string
  nombre: string
  precio: number
  descripcion: string
  nombreCreador: string
  requerimientoDTOSet: RequerimientoDTO[]
}



