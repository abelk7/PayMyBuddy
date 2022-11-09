import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import { Injectable } from '@angular/core';

import {Observable, retry, switchMap} from 'rxjs';

import { AuthService } from './service/auth.service';

import {map} from "rxjs/operators";
import {LoginComponent} from "./login/login.component";
import {environment} from "../environments/environment";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {



  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    req = req.clone({
      setHeaders: {
        'Content-Type' : 'application/x-www-form-urlencoded; charset=utf-8',
        'Accept'       : 'application/json',
        'Authorization': `Bearer ` + AuthService.access_token,
        'RefreshToken' : `` + AuthService.refresh_token
      },
    });

    // @ts-ignore
    return next.handle(req).pipe(map(event => {
      if (event instanceof HttpResponse) {
        if(event.body.access_token != null ||event.body.access_token != undefined) {
          //new token have been generated
          AuthService.access_token = event.body.access_token;
          sessionStorage.setItem("access_token",event.body.access_token);

        }
      }
      return next.handle(req);
    }));
  }


}
