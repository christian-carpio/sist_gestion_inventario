export interface Jwt {
  token: string;
}

export interface UserModel {

  sub: string;
  roles: string[];

}
