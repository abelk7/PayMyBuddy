import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router'
import { User } from '../model/user';
import { AuthService } from '../service/auth.service';
import {first, map} from 'rxjs/operators';
import {environment} from "../../environments/environment";


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
    private http: HttpClient,
    private router:Router,
    private formBuilder: FormBuilder,
    private authenticationService : AuthService,
    private route: ActivatedRoute,
    ) {

    }

  ngOnInit(): void {
  }

  user = new User();

  onSubmit(form: any) {

  this.authenticationService.login(form.controls.email.value, form.controls.password.value)
      .pipe(first())
      .subscribe({
        next: (data) => {
          // switch (data.status) {
          //   case 'success':
          //     this.alertService.success('Vous êtes maintenant connecté.', { keepAfterRouteChange: true });
          //     const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/actualite';
          //     this.router.navigateByUrl(returnUrl);
          //     break;
          //   case 'error':
          //     this.alertService.error(data.message);
          //     this.loading = false;
          //     break;
          //   case 'error-confirm':
          //     console.log("Je suis dans le error confirm");
          //     this.alertService.warn(data.message, { keepAfterRouteChange: true });
          //     this.router.navigate(['/confirm']);
          //     break;

          //   default:
          //     break;
          // }
          // console.log(data);
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  // refreshToken() {
  //   console.log("on bonjour : ");
  //   // this.http.get(`${environment.apiUrl}/token/refresh`)
  //   //   .pipe(map((response) => {
  //   //     console.log(response);
  //   //   }))
  //   console.log(sessionStorage.getItem("access_token"))
  //   this.http.get<any>(`${environment.apiUrl}/token/refresh`).subscribe(data => {
  //     console.log(data);
  //   })
  // }

  bonjour() {
    console.log("on bonjour : ");

    console.log("access_token (session) : "+ sessionStorage.getItem("access_token"));
    console.log("access_token (auth) : "+ AuthService.access_token);
    // this.http.get(`${environment.apiUrl}/token/refresh`)
    //   .pipe(map((response) => {
    //     console.log(response);
    //   }))
    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };

    this.http.get<any>(`${environment.apiUrl}/bonjour`,options).subscribe(data => {
      console.log("MSG : " + data);
    })
  }
}
