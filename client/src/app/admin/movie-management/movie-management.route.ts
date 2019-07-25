import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Movie } from 'app/shared/model/movie.model';
import { MovieService } from './movie-management.service';
import { MovieComponent } from './movie-management.component';
import { MovieDetailComponent } from './movie-management-detail.component';
import { MovieUpdateComponent } from './movie-management-update.component';
import { MovieDeletePopupComponent } from './movie-management-delete-dialog.component';
import { IMovie } from 'app/shared/model/movie.model';

@Injectable({ providedIn: 'root' })
export class MovieResolve implements Resolve<IMovie> {
  constructor(private service: MovieService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovie> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Movie>) => response.ok),
        map((movie: HttpResponse<Movie>) => movie.body)
      );
    }
    return of(new Movie());
  }
}

export const movieRoute: Routes = [
  {
    path: '',
    component: MovieComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Movies'
    },
    canActivate: []
  },
  {
    path: ':id/view',
    component: MovieDetailComponent,
    resolve: {
      movie: MovieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Movies'
    },
    canActivate: []
  },
  {
    path: 'new',
    component: MovieUpdateComponent,
    resolve: {
      movie: MovieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Movies'
    },
    canActivate: []
  },
  {
    path: ':id/edit',
    component: MovieUpdateComponent,
    resolve: {
      movie: MovieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Movies'
    },
    canActivate: []
  }
];

export const moviePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MovieDeletePopupComponent,
    resolve: {
      movie: MovieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Movies'
    },
    canActivate: [],
    outlet: 'popup'
  }
];
