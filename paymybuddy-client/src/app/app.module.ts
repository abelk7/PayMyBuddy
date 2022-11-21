import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http'

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {TransferComponent} from './transfer/transfer.component';
import {ProfileComponent} from './profile/profile.component';
import {ContactComponent} from './contact/contact.component';
import {HomeComponent} from './home/home.component';
import {NgDynamicBreadcrumbModule} from "ng-dynamic-breadcrumb";
import {LoginComponent} from './login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RegisterComponent} from './register/register.component';
import {AuthInterceptor} from './auth.interceptor';
import {AlertComponent} from './alert/alert.component';
import { ajax, css } from "jquery";


@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        TransferComponent,
        ProfileComponent,
        ContactComponent,
        HomeComponent,
        LoginComponent,
        RegisterComponent,
        AlertComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgDynamicBreadcrumbModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule

    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
