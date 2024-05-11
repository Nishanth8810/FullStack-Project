import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../Services/auth.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const auth=inject(AuthService);
  const myToken=auth.getToken();

  if(myToken){
    req=req.clone({
      setHeaders:{Authorization:`Bearer ${myToken}`}
    })
  }
  return next(req);
};
