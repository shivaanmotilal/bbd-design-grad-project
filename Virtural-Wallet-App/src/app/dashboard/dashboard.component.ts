import { Component, OnInit } from '@angular/core';
import { Payment } from '../models/payment';
import { Account } from '../models/account';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  payments: Payment[];
  highestTransaction: number;
  lowestTransaction: number;
  accounts: Account[];

  constructor() { }

  ngOnInit() {
    this.payments = [new Payment("1234", "15478925",5478, "45794544", 4780,254, "15-Jan-2019", "23-Jul-2019", 1), new Payment("1234", "15478925",5478, "45794544", 4780,254, "15-Jan-2019", "23-Jul-2019", 0)];
    this.accounts = [new Account("15478925", 1258.65, 1000.00, 1, this.payments), new Account("153878925", 2458.65, 2000.00, 0, null)];
    this.highestTransaction = 1000;
    this.lowestTransaction = 50;
  }

}
