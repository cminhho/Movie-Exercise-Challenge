import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudAPIClass } from '@app/core/class/CrudAPI.class';

@Injectable()
export class UserService extends CrudAPIClass<any, any> {
  constructor(protected httpClient: HttpClient) {
    super(httpClient);
    this.resourceUrl = '/api/user';
  }
}
