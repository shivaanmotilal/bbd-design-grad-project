import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../login/login.service';
import { Customer } from '../models/customer';
import { Observable } from 'rxjs';
import { NgForm, FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any = {};
  loginForm: any;

  errorMessage: string;
  constructor(private router: Router, private LoginService: LoginService, private formbuilder: FormBuilder) { }


  ngOnInit() {
    sessionStorage.removeItem('UserName');
    sessionStorage.clear();
    this.loginForm = this.formbuilder.group({
      Email: ['', [Validators.required]],
      Password: ['', [Validators.required]],
    });
  }
  
  login() {
    this.router.navigate(['/dashboard']);
  };

}
