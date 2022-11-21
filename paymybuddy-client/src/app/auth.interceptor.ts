import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest,
    HttpResponse
} from '@angular/common/http';
import {ElementRef, Injectable, ViewChild} from '@angular/core';

import {Observable, tap, throwError} from 'rxjs';

import {AuthService} from './service/auth.service';
import {map} from "rxjs/operators";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "./service/alert.service";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    // @ts-ignore
    @ViewChild('closeModal') closeModal: ElementRef

    constructor(
        private router: Router,
        private alertService: AlertService,
        private route: ActivatedRoute
    ) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        req = req.clone({
            setHeaders: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
                'Accept': 'application/json',
                'Authorization': `Bearer ` + sessionStorage.getItem("access_token"),
                'RefreshToken':  `` + sessionStorage.getItem("refresh_token"),
            },
        });

        // @ts-ignore
        // return next.handle(req).pipe(map(event => {
        //     if (event instanceof HttpResponse) {
        //         console.log(event);
        //         console.log("OOOOOOOOOOOOOOOK")
        //         if(event.body.access_token != null || event.body.access_token != undefined){
        //
        //         }
        //     }
        //
        // }));

        // @ts-ignore
        // return next.handle(req).pipe(map(event => {
        //     if (event instanceof HttpResponse) {
        //         if (event.body.access_token != null || event.body.access_token != undefined) {
        //             //new token have been generated
        //             // console.log(event.body.access_token);
        //             // console.log(sessionStorage.getItem("access_token"));
        //
        //             console.log(sessionStorage.getItem("access_token"));
        //             console.log(sessionStorage.getItem("refresh_token"));
        //             console.log(sessionStorage.getItem("userEmail"));
        //
        //             AuthService.access_token = event.body.access_token;
        //             sessionStorage.setItem("access_token", event.body.access_token);
        //             //
        //             if (event.body.refresh_token != null || event.body.refresh_token != undefined) {
        //                 AuthService.refresh_token = event.body.refresh_token;
        //                 sessionStorage.setItem("refresh_token", event.body.refresh_token);
        //             }
        //             if (event.body.refresh_token != null || event.body.refresh_token != undefined) {
        //                 AuthService.refresh_token = event.body.refresh_token;
        //                 sessionStorage.setItem("refresh_token", event.body.refresh_token);
        //             }
        //             if (event.body.userEmail != null || event.body.userEmail != undefined) {
        //                 sessionStorage.setItem("userEmail", event.body.userEmail);
        //             }
        //         }
        //     }
        // }));
        //
        // return next.handle(req);


        // @ts-ignore
        // return next.handle(req).pipe(
        //     map((event: HttpEvent<any>) => {
        //         if (event instanceof HttpResponse) {
        //             console.log("OOOOOOOOOOOOK");
        //             const modEvent = event.clone({ body: "camelCaseObject" });
        //
        //             return modEvent;
        //         }
        //         return null;
        //     })
        // );

        return next.handle(req).pipe(
            tap({
                next: () => null,
                error: (err: HttpErrorResponse) => {
                    console.log("UNE ERREUR EST SURVENUE...");
                    if ([401, 403].includes(err.status)){
                        if(err.error.error_app != undefined && err.error.error_app == "1") {
                            //RefreshToken Expired, need to login
                            //this.closeModal.nativeElement.click();

                            this.router.navigate(['/login'], {queryParams: {message:"Vous devez vous reconnecter"}});
                            // this.redirectAndRefreshPage("/login");

                        }

                    }
                },
            })
        );

    }


    redirectAndRefreshPage(page : string) {
        window.location.href=page;
    }

}

