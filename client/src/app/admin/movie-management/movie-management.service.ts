import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '@env/environment';
import { CrudAPIClass } from '@app/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({ providedIn: 'root' })
export class MovieService extends CrudAPIClass<any, any> {

  constructor(protected http: HttpClient) {
    super(http);
    this.resourceUrl = '/api/movie';
  }

  populateMovieData(req?: any): Observable<any> {
    return this.http
      .post<any>('/api/crawler/movie', {}, { params: req, observe: 'response' });
  }
}
