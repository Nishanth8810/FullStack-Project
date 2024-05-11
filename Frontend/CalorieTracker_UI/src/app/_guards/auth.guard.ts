import {  inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import {  map } from 'rxjs';
import { UserServiceService } from '../Services/user-service.service';



export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const authService = inject(AuthService);
  const toast=inject(NgToastService)
  if (authService.isLoggedIn()) {
    return true;
  } else {
        toast.error({detail:'ERROR',summary:'Try login'})
        router.navigateByUrl('/login');
    return false; 
  }
};

export const adminGuard: CanActivateFn = (route, state) => {

  const router = inject(Router)
  const authService = inject(AuthService);
  const toast=inject(NgToastService)
  if (authService.isAdmin()) {
    return true;
  } else {
        toast.error({detail:'ERROR',summary:'Try login'})
        router.navigateByUrl('/login');
    return false; 
  }
};

export const unAuthorizedGuard: CanActivateFn = (route, state) => {

  const router = inject(Router)
  const authService = inject(AuthService);
  const toast=inject(NgToastService)
  if (true) {
    if (typeof localStorage !== 'undefined') {
      localStorage.clear();
    }
    return true; 
  }
};

export const isBlockedAuthorizedGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const authService = inject(AuthService);
  const userStore = inject(UserServiceService);
  const toast=inject(NgToastService)
  let email=localStorage.getItem('email');

  return authService.isUserBlocked(email).pipe(
    map((res: any) => {
      if (res === 'OK') {
        return true;
      }
      else {
        if (typeof localStorage !== 'undefined') {
          localStorage.clear();
        }
        toast.error({detail:"Error",summary:"login session fail, try login again",duration:5000});
        localStorage.clear();
        router.navigate(['login']); 
        return false;
      }
    }),
  );
  
};
