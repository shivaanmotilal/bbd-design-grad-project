import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { Observable } from 'rxjs';
import { NgForm, FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  data = false;
  UserForm: any;
  massage: string;
  constructor(private loginService: LoginService) { }

  ngOnInit() {
    /*this.UserForm = this.formbulider.group({
      UserName: ['', [Validators.required]],
      LoginName: ['', [Validators.required]],
      Password: ['', [Validators.required]],
      Email: ['', [Validators.required]],
      ContactNo: ['', [Validators.required]],
      Address: ['', [Validators.required]],
    });*/
  }
  
 /* onFormSubmit() {
    const user = this.UserForm.value;
    this.CreateCustomer(user);
  }

  CreateCustomer(register: Customer) {
    this.loginService.CreateUser(register).subscribe(
      () => {
        this.data = true;
        this.massage = 'Customer Created Successfully';
        this.UserForm.reset();
      });
  }*/

}
