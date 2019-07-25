import { Injectable } from '@angular/core';
import { BaseCrudAPIClass } from '@app/core/class/baseCrudAPI.class';
import { environment } from '@env/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService extends BaseCrudAPIClass<any, any> {
  private SERVER_API_URL = environment.serverUrl;
  constructor(protected http: HttpClient) {
    super(http);
    this.resourceUrl = this.SERVER_API_URL;
  }

  query(type: string, req?: any): Observable<any> {
    return this.http.get<any[]>(this.resourceUrl + '/' + type, { params: req, observe: 'response' });
  }
}
