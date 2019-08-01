import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  transactions = null;
  highestTransaction = null;
  lowestTransaction = null;
  accountBalance = null;

  constructor() { }

  ngOnInit() {
    this.transactions = [".....", "....", "....","", ""];
    this.highestTransaction = 1000.00;
    this.lowestTransaction = 20.00;
    this.accountBalance = 800.65
  }

}
