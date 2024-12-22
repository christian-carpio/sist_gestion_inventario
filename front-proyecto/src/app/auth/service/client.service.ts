import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Login} from "../interfaces/Login";
import {Observable, tap} from "rxjs";
import {Jwt, UserModel} from "../interfaces/Jwt";
import {Register} from "../interfaces/Register";
import {urlMsvcUser} from "../../constants/Constants";
import {Client, UpdateUser} from "../interfaces/Client";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  urlApi = `${urlMsvcUser}/client`;

  constructor(private router: Router, private http: HttpClient) {}



  public getInfo(name: string): Observable<Client> {
    return this.http.get<Client>(`${this.urlApi}/getInfoUser/${name}`);
  }
  public putInfo(updateUser: UpdateUser, name: string): Observable<any> {
    return this.http.put<any>(`${this.urlApi}/${name}`,updateUser );
  }
}
