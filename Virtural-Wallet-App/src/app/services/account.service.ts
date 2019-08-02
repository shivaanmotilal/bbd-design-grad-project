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
    const headers = new HttpHeaders({"Content-Type": "application/json", "user-id": "5003363", "password":"Khaki"});
    return this.backendService.get(this.path, headers).pipe( // +"5003363"
      map(a => {
        return a;
      }));
  }

}
