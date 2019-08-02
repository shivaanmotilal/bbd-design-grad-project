import { Component, OnInit } from '@angular/core';
import { Payment } from '../models/payment';
import { PaymentService } from '../services/payment.service';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  payments: Payment[];

  constructor(private paymentService: PaymentService, private accountService: AccountService) { }

  ngOnInit() {
    // this.payments = [new Payment("1234", "15478925",5478, "45794544", 4780,254, "15-Jan-2019", "23-Jul-2019", 1), new Payment("1234", "15478925",5478, "45794544", 4780,254, "15-Jan-2019", "23-Jul-2019", 0)];
    this.getAccounts();
  }

  getAccounts() {
    let accounts = [];
    this.payments = [];
    this.accountService.getAccounts().subscribe((data) => {
      accounts = data;
      accounts.forEach(element => {
        this.paymentService.getPayments(element["account-number"]).subscribe((data1) => {
          data1.forEach(element1 => {
            this.payments.push(element1);
          });
        });
      });
    });
  }

}
