import { Injectable } from '@angular/core';
import { BackEndService } from './back-end.service';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/internal/operators/map';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  path = '/wallet/api/payments/';

  constructor(private backendService: BackEndService) { }
  
  private extractData(res: Response) {
    let body = res;
    return body || { };
  }

  getPayments(): Observable<any> {
    return this.backendService.get(this.path).pipe(
      map(this.extractData));
  }
}
