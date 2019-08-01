import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { HistoryComponent } from './history/history.component';
import { ProfileComponent } from './profile/profile.component';

export const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent,
    data: {
      title: 'Virtual Wallet'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Login Page'
    }
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    data: {
      title: 'Dashboard'
    }
  },
  {
    path: 'register',
    component: RegisterComponent,
    data: {
      title: 'Add User Page'
    }
  },
  {
    path: 'history',
    component: HistoryComponent,
    data: {
      title: 'History'
    }
  },
  {
    path: 'profile',
    component: ProfileComponent,
    data: {
      title: 'Profile'
    }
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
