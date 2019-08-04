import { Injectable } from '@angular/core';
import { CrudAPIClass } from '@app/core/class/CrudAPI.class';
import { environment } from '@env/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService extends CrudAPIClass<any, any> {
  constructor(protected http: HttpClient) {
    super(http);
    this.resourceUrl = '/api/movie';
  }

  query(type: string, req?: any): Observable<any> {
    return this.http.get<any[]>(this.resourceUrl + '/' + type, { params: req, observe: 'response' });
  }
}
