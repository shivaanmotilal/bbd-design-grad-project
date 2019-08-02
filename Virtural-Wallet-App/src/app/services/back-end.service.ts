import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BackEndService {

    url: string = 'localhost:8080'; 
    
    constructor(private http: HttpClient) { // AngularHttp

    }

    get(path: string, otherUrl: boolean = false) {
        if (!otherUrl)
            return this.http.get(this.url + path);
        else
        {
            return this.http.get(path);
        } 
    }
    
    post(path: string, data: any) {
        return this.http.post(this.url + path, JSON.stringify(data), {
            headers: new HttpHeaders({'Content-Type': 'application/json'})
        });
    }
}