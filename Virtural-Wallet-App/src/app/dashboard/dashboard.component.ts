import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Payment } from '../models/payment';
import { Account } from '../models/account';
import { AccountService } from '../services/account.service';
import { PaymentService } from '../services/payment.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  payments: any = [];
  highestTransaction: number = 0;
  lowestTransaction: number = 0;
  accounts: any = [];

  constructor(public accountService: AccountService, public paymentService: PaymentService)
  { }

  ngOnInit() {
    this.getAccounts();
  }

  getAccounts() {
    this.accounts = [];
    this.payments = [];
    this.accountService.getAccounts().subscribe((data: {}) => {
      this.accounts = data;
      this.accounts.forEach(element => {
        this.paymentService.getPayments(element["account-number"]).subscribe((data1) => {
          data1.forEach(element1 => {
            this.payments.push(element1);
          });
          this.getHighestTransaction();
          this.getLowestTransaction();
        });
      });
    });
  }

  getHighestTransaction() {
    this.highestTransaction = this.payments[0].amount;
    for(var i = 1; i < this.payments.length; i++) {
      if(this.payments[i].amount > this.highestTransaction) {
        this.highestTransaction = this.payments[i].amount
      }
    }
  }

  getLowestTransaction() {
    this.lowestTransaction = this.payments[0].amount;
    for(var i = 1; i < this.payments.length; i++) {
      if(this.payments[i].amount < this.lowestTransaction) {
        this.lowestTransaction = this.payments[i].amount
      }
    }
  }

}
