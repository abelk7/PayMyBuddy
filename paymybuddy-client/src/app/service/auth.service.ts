import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { User } from '../model/user';
import { first } from 'rxjs/operators';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  static access_token : any;
  static refresh_token : any;


  constructor(private http: HttpClient) {
    if(sessionStorage.getItem("access_token") != null){
      AuthService.access_token =  sessionStorage.getItem("access_token");
    }
    if(sessionStorage.getItem("refresh_token") != null) {
      AuthService.refresh_token = sessionStorage.getItem("refresh_token");
    }
  }


  login(email: string, password: string) {

    let body = new URLSearchParams();
    body.set('email', email);
    body.set('password', password);

    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };

    return this.http.post<any>(`${environment.apiUrl}/login`, body.toString(), options)
    .pipe(map((response) => {
        // if (user.status === 'success') {
        //     //this.service.setValue(true);
        //     // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
        //     user.authdata = window.btoa(login + ':' + password);
        //     //sessionStorage.setItem('currentUser', JSON.stringify(user));
        //     //this.currentUserSubject.next(user);
        //   }

        sessionStorage.setItem("access_token", response.access_token);
        sessionStorage.setItem("refresh_token", response.refresh_token);

          return response;
        })
      );

  }
}
