import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Login} from "../interfaces/Login";
import {Observable, tap} from "rxjs";
import {Jwt, UserModel} from "../interfaces/Jwt";
import {Register} from "../interfaces/Register";
import {urlMsvcUser} from "../../constants/Constants";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  urlApi = `${urlMsvcUser}/auth`;

  constructor(private router: Router, private http: HttpClient) {
  }

  public login(login: Login): Observable<Jwt> {
    return this.http.post<Jwt>(`${this.urlApi}/login`, login)
      .pipe(
        tap(jwt => this.setToken(jwt))
      );
  }

  public register(register: Register): Observable<void> {
    return this.http.post<void>(`${this.urlApi}/register`, register);
  }

  public logOut(): void {
    this.remove();
    this.router.navigateByUrl('/');
  }


  public setToken(jwt: Jwt): void {
    localStorage.setItem("token", jwt.token);
  }

  public remove(): void {
    localStorage.clear();
  }

  public getUser(): UserModel | null {
    const token = this.getToken();
    if (!token) {
      return null
    }
    return JSON.parse(atob(token.split('.')[1])) as UserModel;
  }

  public getToken(): string {
    return localStorage.getItem('token')!;
  }
}
