import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Movie } from 'app/shared/model/movie.model';
import { MovieService } from './movie-management.service';
import { MovieComponent } from './list/movie-management.component';
import { MovieDetailComponent } from './detail/movie-management-detail.component';
import { MovieUpdateComponent } from './update/movie-management-update.component';
import { MovieDeletePopupComponent } from './delete/movie-management-delete-dialog.component';
import { IMovie } from 'app/shared/model/movie.model';

@Injectable({ providedIn: 'root' })
export class MovieResolve implements Resolve<IMovie> {
  constructor(private service: MovieService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovie> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.findById(id).pipe(
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
    canActivate: []
  },
  {
    path: ':id/view',
    component: MovieDetailComponent,
    resolve: {
      movie: MovieResolve
    },
    canActivate: []
  },
  {
    path: 'new',
    component: MovieUpdateComponent,
    resolve: {
      movie: MovieResolve
    },
    canActivate: []
  },
  {
    path: ':id/edit',
    component: MovieUpdateComponent,
    resolve: {
      movie: MovieResolve
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
    canActivate: [],
    outlet: 'popup'
  }
];
