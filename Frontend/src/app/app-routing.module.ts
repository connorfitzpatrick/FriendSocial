import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  {path: "login", component: LoginFormComponent},
  {path:"home", component: HomeComponent, canActivate: [AuthGuard]},
  // Automatically direct to login page if path value is empty
  {path:'',redirectTo:'/login', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
