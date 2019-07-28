import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ErrorMessageService } from '@app/core/service';
import { environment } from '@env/environment';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

const credentialsKey = 'credentials';

/**
 * Adds a default error handler to all requests.
 */
@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
  constructor(
    private router: Router,
    private errorMessageService: ErrorMessageService
  ) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next
      .handle(request)
      .pipe(catchError(error => this.errorHandler(error)));
  }

  // Customize the default error handler here if needed
  private errorHandler(
    response: HttpResponse<any>
  ): Observable<HttpEvent<any>> {
    if (response.status === 401) {
    } else if (response.status === 400) {
      // do nothing
    }

    throw response;
  }
}
