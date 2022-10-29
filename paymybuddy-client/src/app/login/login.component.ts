import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router'
import { User } from '../model/user';
import { AuthService } from '../service/auth.service';
import { first } from 'rxjs/operators';


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
}
