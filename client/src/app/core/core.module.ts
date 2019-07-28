import { CommonModule } from '@angular/common';
import {
  HTTP_INTERCEPTORS,
  HttpClient,
  HttpClientModule
} from '@angular/common/http';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {
  ErrorHandlerInterceptor,
  HttpService
} from '@app/core/http';
import {
  ErrorMessageService,
  UserService,
  UtilService
} from '@app/core/service';
import { ApiKeyInterceptor } from './http/api-key.interceptor';


@NgModule({
  imports: [CommonModule, HttpClientModule, RouterModule],
  declarations: [],
  providers: [
    ErrorHandlerInterceptor,
    UserService,
    UtilService,
    ErrorMessageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiKeyInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HttpClient,
      useClass: HttpService
    }
  ]
})
export class CoreModule { }
