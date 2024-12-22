export interface InsumoDetalleDTO {
  id: number
  nombre: string
  descripcion: string
  insumoDTOSet: InsumoDTO[]
}

export interface InsumoDTO {
  id: number
  cantidad: number
  fechaFabricacion: string
  fechaExpiracion: string
  esCaducado: boolean
  insumoIdpk: number
}
