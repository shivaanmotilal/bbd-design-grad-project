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
        console.log('Get path: ', completePath);
        const receive = this.http.get(completePath);
        console.log('Get receive: ', receive);
        return receive;
    }
    
    post(path: string, data: any) {
        const completePath = this.url + path;
        console.log('Post path: ', completePath);
        const receive = this.http.post(completePath, JSON.stringify(data), {
            headers: new HttpHeaders({'Content-Type': 'application/json'})
        });
        console.log('Post receive: ', receive);
        return receive;
    }
}