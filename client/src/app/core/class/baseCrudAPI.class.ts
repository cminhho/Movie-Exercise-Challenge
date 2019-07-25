import { IBaseCrudApi } from '../model/base-crud-api.interface';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable()
export abstract class BaseCrudAPIClass<T, ID> implements IBaseCrudApi<T, ID> {
  resourceUrl: string;
  constructor(protected http: HttpClient) {}

  create(payload: T): Observable<HttpResponse<T>> {
    return this.http
      .post<T>(this.resourceUrl, payload, { observe: 'response' })
      .pipe(map((res: HttpResponse<T>) => res));
  }

  update(id: number, payload: T): Observable<HttpResponse<T>> {
    return this.http
      .put<T>(this.resourceUrl + '/' + id, payload, { observe: 'response' })
      .pipe(map((res: HttpResponse<T>) => res));
  }

  query(req?: any): Observable<HttpResponse<T[]>> {
    return this.http
      .get<T[]>(this.resourceUrl, {
        params: req,
        observe: 'response'
      })
      .pipe(map((res: HttpResponse<T[]>) => res));
  }

  find(id: ID): Observable<HttpResponse<T>> {
    return this.http
      .get<T>(`${this.resourceUrl}/${id}`, {
        observe: 'response'
      })
      .pipe(map((res: HttpResponse<T>) => res));
  }

  delete(id: ID): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {
      observe: 'response'
    });
  }

  deleteItems(items: T[]): Observable<HttpResponse<T>> {
    return this.http
      .put<T>(`${this.resourceUrl}/delete/items`, items, { observe: 'response' })
      .pipe(map((res: HttpResponse<T>) => res));
  }

  deleteAll(): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/all`, {
      observe: 'response'
    });
  }

  protected convertDateArrayFromServer(
    res: HttpResponse<T[]>
  ): HttpResponse<T[]> {
    return res;
  }
}
