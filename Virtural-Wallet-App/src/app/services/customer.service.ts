import { Injectable } from '@angular/core';
import { map } from 'rxjs/internal/operators/map';
import { Observable } from 'rxjs/internal/Observable';
import { BackEndService } from './back-end.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  path = '/wallet/api/customer/';

  constructor(private backendService: BackEndService) { }
  
  private extractData(res: Response) {
    let body = res;
    return body || { };
  }

  getCustomer(customerId): Observable<any> {
    return this.backendService.get(this.path + customerId).pipe(
      map(this.extractData));
  }
}
