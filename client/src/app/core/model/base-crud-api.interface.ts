import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface IBaseCrudApi<T, ID> {
  create(payload: T): Observable<HttpResponse<T>>;

  update(payload: T): Observable<HttpResponse<T>>;

  updateByID(id: number, payload: T): Observable<HttpResponse<T>>;

  query(req: any): Observable<HttpResponse<T[]>>;

  find(id: ID): Observable<HttpResponse<T>>;

  delete(id: ID): Observable<HttpResponse<any>>;

  deleteAll(): Observable<HttpResponse<any>>;
}
