export interface Client {
  name: string
  username: string
  email: string
  puntoLealtad: number
  urlImage: string
}
export interface UpdateUser {
  name: string
  password: string
  image: string
}
