import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { from, Observable } from 'rxjs';
import { Customer } from '../models/customer';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  Url: string;
  token: string;
  header: any;

  constructor(private http: HttpClient) {
    this.Url = '';

    const headerSettings: {[name: string]: string | string[]; } = {};
    this.header = new HttpHeaders(headerSettings);
  }

  Login(model: any) {
    debugger;
    var a = this.Url+'';
    return this.http.post<any>(a, model, {headers: this.header});
  }

  CreateUser(customer: Customer) {
    const httpOptions = {headers: new HttpHeaders({ 'Content-Type': 'application/json'})};
    return this.http.post<Customer[]>(this.Url+'', customer, httpOptions);
  }
}
