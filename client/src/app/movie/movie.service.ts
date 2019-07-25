import { Injectable } from '@angular/core';
import { BaseCrudAPIClass } from '@app/core/class/baseCrudAPI.class';
import { environment } from '@env/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MovieService extends BaseCrudAPIClass<any, any> {
  private SERVER_API_URL = environment.serverUrl;
  constructor(protected http: HttpClient) {
    super(http);
    this.resourceUrl = this.SERVER_API_URL + '/popular';
  }
}
