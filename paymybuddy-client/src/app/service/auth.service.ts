import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from 'src/environments/environment';
import {map} from 'rxjs/operators';
import {Router} from "@angular/router";
import {resolve} from "@angular/compiler-cli";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    static access_token: any = "";
    static refresh_token: any = "";


    constructor(private http: HttpClient, private router: Router) {
        if (sessionStorage.getItem("access_token") != null) {
            AuthService.access_token = sessionStorage.getItem("access_token");
        }
        if (sessionStorage.getItem("refresh_token") != null) {
            AuthService.refresh_token = sessionStorage.getItem("refresh_token");
        }
    }


    login(email: string, password: string) {

        let body = new URLSearchParams();
        body.set('email', email);
        body.set('password', password);

        // let options = {
        //   headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
        // };

        return this.http.post<any>(`${environment.apiUrl}/login`, body.toString())
            .pipe(map((response) => {
                    // if (user.status === 'success') {
                    //     //this.service.setValue(true);
                    //     // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
                    //     user.authdata = window.btoa(login + ':' + password);
                    //     //sessionStorage.setItem('currentUser', JSON.stringify(user));
                    //     //this.currentUserSubject.next(user);
                    //   }
                    // console.log(response);
                    // sessionStorage.setItem("access_token", response.access_token);
                    // sessionStorage.setItem("refresh_token", response.refresh_token);
                    //
                    //
                    //
                    //   return response;
                })
            );

    }

    login2(email: string, password: string) {
        let body = new URLSearchParams();
        body.set('email', email);
        body.set('password', password);

        return new Promise((resolve, reject) => {
            this.http.post<any>(`${environment.apiUrl}/login`, body).subscribe({
                next: (respose) => {
                    resolve(respose);
                },
                error: (error) => {
                    reject(error.error);
                }
            })
        })
    }
}
