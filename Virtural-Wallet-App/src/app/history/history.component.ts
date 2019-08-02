import { Component, OnInit } from '@angular/core';
import { Payment } from '../models/payment';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  payments: Payment[];

  constructor() { }

  ngOnInit() {
    const paymentsFromBackEnd = 
    this.payments = [new Payment("1234", "15478925",5478, "45794544", 4780,254, "15-Jan-2019", "23-Jul-2019", 1), new Payment("1234", "15478925",5478, "45794544", 4780,254, "15-Jan-2019", "23-Jul-2019", 0)];
  }

}
