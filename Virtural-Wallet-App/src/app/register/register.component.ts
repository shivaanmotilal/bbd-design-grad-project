import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login/login.service';
import { Customer } from '../models/customer';
import { Observable } from 'rxjs';
import { NgForm, FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  data = false;
  customerForm: any;
  massage: string;
  constructor(private formbuilder: FormBuilder, private loginService: LoginService) { }

  ngOnInit() {
    this.customerForm = this.formbuilder.group({
      Email: ['', [Validators.required]],
      Name: ['', [Validators.required]],
      Surname: ['', [Validators.required]],
      Contact: ['', [Validators.required]],
      Password: ['', [Validators.required]],
      PasswordConfirm: ['', [Validators.required]],
    });
  }
  
  onFormSubmit() {
    const user = this.customerForm.value;
    this.CreateCustomer(user);
  }

  CreateCustomer(register: Customer) {
    this.loginService.CreateUser(register).subscribe(
      () => {
        this.data = true;
        this.massage = 'Customer Created Successfully';
        this.customerForm.reset();
      });
  }
}
