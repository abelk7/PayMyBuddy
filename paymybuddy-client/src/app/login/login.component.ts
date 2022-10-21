import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router'
import { User } from '../model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;

  foorm = this.formBuilder.group ({
    email: [''],
    password:['']
  });


  constructor(
    private httpClient: HttpClient,
    private router:Router,
    private formBuilder: FormBuilder
    ) {

    }

  ngOnInit(): void {
  }

  user = new User();

  onSubmit(form: any) {
    this.httpClient.post('http://localhost:8080/login', {
      'email':form.controls.email.value,
      'password': form.controls.password.value
    })
    .subscribe( () => {
          this.router.navigate(['/login']);
      });
      console.log("Formulaire inscription envoyé");
  }
}
