import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { ContactComponent } from './contact/contact.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { TransferComponent } from './transfer/transfer.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent,
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
  { path: 'transfer', component: TransferComponent,
  data: {
    title: 'transfer',
    breadcrumb: [
      {
        label: 'Home',
        url: '/home'
      },
      {
        label: 'Transfer',
        url: '/transfer'
      }
    ]
  }
  },
  { path: 'profile', component: ProfileComponent,
  data: {
    title: 'profile',
    breadcrumb: [
      {
        label: 'Home',
        url: '/home'
      },
      {
        label: 'Profile',
        url: '/profile'
      }
    ]
  }
  },
  { path: 'contact', component: ContactComponent,
  data: {
    title: 'contact',
    breadcrumb: [
      {
        label: 'Home',
        url: '/home'
      },
      {
        label: 'Contact',
        url: '/contact'
      }
    ]
  }
  },
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: '**', redirectTo: '/home', pathMatch: 'full'}
];

// const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
