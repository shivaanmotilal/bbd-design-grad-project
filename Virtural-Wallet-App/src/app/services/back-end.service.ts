import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BackEndService {

    url: string = 'localhost:8080/wallet/api/'; 
    
    constructor(private http: HttpClient) { // AngularHttp

    }

    get(path: string) {
        const completePath = this.url + path;
        return this.http.get();
    }
    
    post(path: string, data: any) {
        const completePath = this.url + path;
        return this.http.post(completePath, JSON.stringify(data), {
            headers: new Headers({'Content-Type': 'application/json'})
        });
    }
}