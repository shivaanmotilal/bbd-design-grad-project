import { Injectable } from '@angular/core';
import { BackEndService } from './back-end.service';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  path = '';

  constructor(private backEndService: BackEndService) { }


}
