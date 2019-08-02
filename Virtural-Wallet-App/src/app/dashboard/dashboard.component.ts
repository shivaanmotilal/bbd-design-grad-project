import { Component, OnInit } from '@angular/core';
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

  constructor(public accountService: AccountService, public paymentService: PaymentService) { }

  ngOnInit() {
    this.getAccounts();
    this.getPayments();
    this.getHighestTransaction();
    this.getLowestTransaction();
  }

  getAccounts() {
    this.accounts = [];
    this.accountService.getAccounts().subscribe((data: {}) => {
      this.accounts = data;
    });
  }

  getPayments() {
    this.payments = [];
    this.paymentService.getPayments().subscribe((data: {}) => {
      this.payments = data;
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
