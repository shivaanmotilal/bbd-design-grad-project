import { Injectable } from '@angular/core';
import { BackEndService } from './back-end.service';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/internal/operators/map';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  path = '/wallet/api/Payments/';

  constructor(private backendService: BackEndService) { }
  
  private extractData(res: Response) {
    let body = res;
    return body || { };
  }

  getPayments(accNo: string = null): Observable<any> {
    const headers = new HttpHeaders ({"Content-Type": "application/json", "user-id": "5003363", "password":"Khaki", "account-number": accNo});
    return this.backendService.get(this.path, headers).pipe(
      map(a => {
        console.log("a", a);
        return a;
      }));
  }

  // getPaymentById(id): Observable<any> {
  //   const headers = new HttpHeaders ({"Content-Type": "application/json", "user-id": "5003363", "password":"Khaki"});
  //   return this.backendService.get(this.path, headers).pipe(
  //     map(this.extractData));
  // }
}
