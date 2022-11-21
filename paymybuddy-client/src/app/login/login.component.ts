import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router'
import {User} from '../model/user';
import {AuthService} from '../service/auth.service';
import {first} from 'rxjs/operators';
import {environment} from "../../environments/environment";
import {AlertService} from "../service/alert.service";
import {error} from "@angular/compiler-cli/src/transformers/util";


@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {



    form!: FormGroup;

    foorm = this.formBuilder.group({
        email: [''],
        password: ['']
    });
    user = new User();

    constructor(
        private http: HttpClient,
        private router: Router,
        private formBuilder: FormBuilder,
        private authenticationService: AuthService,
        private route: ActivatedRoute,
        private alertService: AlertService,
    ) {

    }

    ngOnInit(): void {
        $( "div" ).removeClass( "modal-backdrop fade show" );

        this.route.queryParams
            .subscribe(params => {
                this.alertService.warn(params.message, {keepAfterRouteChange: true});
            })

    }

    onSubmit(form: any) {

        this.authenticationService.login2(form.controls.email.value, form.controls.password.value)
            //if success
            .then((data: any) => {
                console.log(data.access_token);
                //
                if(data.access_token != undefined && data.refresh_token != undefined) {
                    sessionStorage.setItem("access_token", data.access_token);
                    sessionStorage.setItem("refresh_token", data.refresh_token);
                }
                if(data.userEmail != undefined){
                    sessionStorage.setItem("userEmail", data.userEmail);

                }

                this.alertService.success('Vous êtes maintenant connecté.', { keepAfterRouteChange: true});
                this.router.navigate(['/home']);

                //if error
            }, (error) => {
                // Rejet de la promesse
                if(error.error == '401') {
                    this.alertService.error(error.message, {});
                }
            });
    }

}
