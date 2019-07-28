import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '@env/environment';
import { BaseCrudAPIClass } from '@app/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({ providedIn: 'root' })
export class MovieService extends BaseCrudAPIClass<any, any> {

  private SERVER_API_URL = environment.serverUrl;
  constructor(protected http: HttpClient) {
    super(http);
    this.resourceUrl = this.SERVER_API_URL + 'api/movie';
  }

  populateMovieData(req?: any): Observable<any> {
    return this.http
      .post<any>(this.SERVER_API_URL + 'api/crawler/movie', {}, { params: req, observe: 'response' });
  }
}
