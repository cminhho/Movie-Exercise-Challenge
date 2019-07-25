import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '@env/environment';
import { IMovie } from 'app/shared/model/movie.model';

type EntityResponseType = HttpResponse<IMovie>;
type EntityArrayResponseType = HttpResponse<IMovie[]>;

@Injectable({ providedIn: 'root' })
export class MovieService {
  private SERVER_API_URL = environment.serverUrl;
  public resourceUrl = this.SERVER_API_URL + 'api/movies';

  constructor(protected http: HttpClient) {}

  create(movie: IMovie): Observable<EntityResponseType> {
    return this.http.post<IMovie>(this.resourceUrl, movie, { observe: 'response' });
  }

  update(movie: IMovie): Observable<EntityResponseType> {
    return this.http.put<IMovie>(this.resourceUrl, movie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMovie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    return this.http.get<IMovie[]>(this.resourceUrl, { params: req, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
