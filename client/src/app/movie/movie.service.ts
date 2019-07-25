import { Injectable } from '@angular/core';
import { BaseCrudAPIClass } from '@app/core/class/baseCrudAPI.class';
import { environment } from '@env/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MovieService extends BaseCrudAPIClass<any,any>{
  private SERVER_API_URL = environment.serverUrl;

  constructor(protected http: HttpClient) {
    super(http);
    // this.resourceUrl = this.SERVER_API_URL + '/movies';
    this.resourceUrl = "https://api.themoviedb.org/4/list/1?api_key=a7b3c9975791294647265c71224a88ad";
  }
}
