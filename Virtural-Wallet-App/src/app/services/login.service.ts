import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { from, Observable } from 'rxjs';
import { Customer } from '../models/customer';
import { BackEndService } from './back-end.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  path: string;

  constructor(private backendService: BackEndService) {
  }

  Login(model: any) {
    return this.backendService.post(this.path, model);
  }

  CreateUser(customer: Customer) {
    return this.backendService.post(this.path, customer);
  }
}
