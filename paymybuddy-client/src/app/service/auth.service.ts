import { HttpClient } from '@angular/common/http';
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

  // private currentUserSubject: BehaviorSubject<User>;
  // public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    // this.currentUserSubject = new BehaviorSubject<User>(
    //   JSON.parse(sessionStorage.getItem('token'))
    // );
    // this.currentUser = this.currentUserSubject.asObservable();
    //JSON.parse(sessionStorage.)
  }

  /**
   * Login
   */
  // login(login: string, password :string){
  //   // return this.http.post<any>(`${environment.apiUrl}/login`, {"login":login, "password":password})
  //   // .subscribe( (result) => {
  //   //   console.log("Utilisateur connecté, avec login"+login +" pass:"+password);
  //   //   sessionStorage.setItem('token', JSON.stringify(result))
  //   // })
  //   this.http.get(`${environment.apiUrl}/login`).pipe(first()).subscribe({
  //     next: (data) => {
  //       console.log(data);
  //     }
  //   })


  // }
  login(login: string, password: string) {
    return this.http.post<any>(`${environment.apiUrl}/login`, { login, password })
    .pipe(map((user) => {
        // if (user.status === 'success') {
        //     //this.service.setValue(true);
        //     // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
        //     user.authdata = window.btoa(login + ':' + password);
        //     //sessionStorage.setItem('currentUser', JSON.stringify(user));
        //     //this.currentUserSubject.next(user);
        //   }

        console.log(user);

          return user;
        })
      );

  }
}
