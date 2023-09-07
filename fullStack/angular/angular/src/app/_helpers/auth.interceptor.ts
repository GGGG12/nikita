import { HTTP_INTERCEPTORS, HttpErrorResponse, HttpEvent, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

import { TokenStorageService } from '../_services/token-storage.service';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

 const TOKEN_HEADER_KEY = 'Authorization';       // for Spring Boot back-end
//const TOKEN_HEADER_KEY = 'x-access-token';   // for Node.js Express back-end

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  router: any;
  constructor(private token: TokenStorageService,router: Router) {  
    this.router=router; }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  

    // let authReq = req;
    // const token = this.token.getToken();
    // if (token != null) {
    //   // for Spring Boot back-end
    //    authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });

    //   // for Node.js Express back-end
    //   //authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, token) });
    // }
    // return next.handle(authReq);

    let authReq = req;
    
    const token = this.token.getToken();
    if (token != null) {
        authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
       
    }
    return next.handle(authReq)
      .pipe(
      tap(

        (event:any) => {
          if (event instanceof HttpResponse)
            console.log('Server response')
        },
        (err:any) => {
          if (err instanceof HttpErrorResponse) {
            if (err.status == 401) {
              console.log('Unauthorized')
              this.router.navigate(["home"])
              return  next.handle(authReq);
            }
            if (err.status == 400)
            {
              return next.handle(authReq);
            }
          }
            else return next.handle(authReq);
          return next.handle(authReq);
        }
      )
    )
}

  }


export const authInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
];