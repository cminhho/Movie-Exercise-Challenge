import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseCrudAPIClass } from '@app/core/class/baseCrudAPI.class';

@Injectable()
export class UserService extends BaseCrudAPIClass<any, any> {
  constructor(protected httpClient: HttpClient) {
    super(httpClient);
    this.resourceUrl = '/user';
  }
}
