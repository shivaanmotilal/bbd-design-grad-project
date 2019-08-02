import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { BackEndService } from '../services/back-end.service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  path = '/wallet/api/accounts/';

  constructor(private backendService: BackEndService) { }
  
  private extractData(res: Response) {
    let body = res;
    return body || { };
  }

  getAccounts(): Observable<any> {
    return this.backendService.get(this.path).pipe(
      map(this.extractData));
  }

}
