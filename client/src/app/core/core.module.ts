import { CommonModule } from '@angular/common';
import {
  HTTP_INTERCEPTORS,
  HttpClient,
  HttpClientModule
} from '@angular/common/http';
import { NgModule } from '@angular/core';
import { RouteReuseStrategy, RouterModule } from '@angular/router';
import {
  ErrorHandlerInterceptor,
  HttpService
} from '@app/core/http';
import { LocalStorageService } from '@app/core/local-storage.service';
import { RouteReusableStrategy } from '@app/core/route-reusable-strategy';
import {
  ErrorMessageService,
  UserService,
  UtilService
} from '@app/core/service';


@NgModule({
  imports: [CommonModule, HttpClientModule, RouterModule],
  declarations: [],
  providers: [
    LocalStorageService,
    ErrorHandlerInterceptor,
    UserService,
    UtilService,
    ErrorMessageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HttpClient,
      useClass: HttpService
    },
    {
      provide: RouteReuseStrategy,
      useClass: RouteReusableStrategy
    }
  ]
})
export class CoreModule {}
