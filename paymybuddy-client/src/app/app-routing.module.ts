import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ContactComponent} from './contact/contact.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {ProfileComponent} from './profile/profile.component';
import {RegisterComponent} from './register/register.component';
import {TransferComponent} from './transfer/transfer.component';

const routes: Routes = [
    {
        path: 'home', component: HomeComponent,
        data: {
            title: 'home',
            breadcrumb: [
                {
                    label: 'Home',
                    url: ''
                }
            ]
        }
    },
    {
        path: 'transfer', component: TransferComponent,
        data: {
            title: 'transfer',
            breadcrumb: [
                {
                    label: 'Home',
                    url: 'home'
                },
                {
                    label: 'Transfer',
                    url: ''
                }
            ]
        }
    },
    {
        path: 'profile', component: ProfileComponent,
        data: {
            title: 'profile',
            breadcrumb: [
                {
                    label: 'Home',
                    url: 'home'
                },
                {
                    label: 'Profile',
                    url: ''
                }
            ]
        }
    },
    {
        path: 'contact', component: ContactComponent,
        data: {
            title: 'contact',
            breadcrumb: [
                {
                    label: 'Home',
                    url: 'home'
                },
                {
                    label: 'Contact',
                    url: ''
                }
            ]
        }
    },
    {
        path: 'login', component: LoginComponent,
        data: {
            title: 'login',
            breadcrumb: [
                {
                    label: 'Home',
                    url: 'home'
                },
                {
                    label: 'Login',
                    url: ''
                }
            ]
        }
    },
    {
        path: 'register', component: RegisterComponent,
        data: {
            title: 'register',
            breadcrumb: [
                {
                    label: 'Home',
                    url: 'home'
                },
                {
                    label: 'Register',
                    url: ''
                }
            ]
        }
    },
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: '**', redirectTo: '/home', pathMatch: 'full'}
];

// const routes: Routes = [];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
