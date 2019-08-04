import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ICrudAPI<T, ID> {
  create(payload: T): Observable<HttpResponse<T>>;

  update(payload: T): Observable<HttpResponse<T>>;

  updateByID(id: number, payload: T): Observable<HttpResponse<T>>;

  query(req: any): Observable<HttpResponse<T[]>>;

  findById(id: ID): Observable<HttpResponse<T>>;

  delete(payload: T): Observable<HttpResponse<any>>;

  deleteById(id: ID): Observable<HttpResponse<any>>;

  deleteEntities(payloads: T[]): Observable<HttpResponse<any>>;

  deleteAll(): Observable<HttpResponse<any>>;
}
