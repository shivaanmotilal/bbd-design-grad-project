import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BackEndService {

    url: string = 'localhost:8080'; 
    
    constructor(private http: HttpClient) { // AngularHttp

    }

    get(path: string) {
        const completePath = this.url + path;
        return this.http.get(completePath);
    }
    
    post(path: string, data: any) {
        const completePath = this.url + path;
        return this.http.post(completePath, JSON.stringify(data), {
            headers: new HttpHeaders({'Content-Type': 'application/json'})
        });
    }
}