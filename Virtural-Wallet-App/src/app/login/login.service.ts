import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { from, Observable } from 'rxjs';
import { Customer } from '../models/customer';
import { BackEndService } from '../services/back-end.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  path: string = 'login/';

  constructor(private backendService: BackEndService) {
  }

  Login(model: LoginModel) {
    const uniquePath = this.path + '';
    return this.backendService.post(uniquePath, model);
  }

  CreateUser(customer: Customer) {
    const uniquePath = this.path + '';
    return this.backendService.post(uniquePath, customer);
  }
}
