import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeanangularSharedModule } from 'app/shared';
import {
  MovieComponent,
  MovieDetailComponent,
  MovieUpdateComponent,
  MovieDeletePopupComponent,
  MovieDeleteDialogComponent,
  movieRoute,
  moviePopupRoute
} from './';

const ENTITY_STATES = [...movieRoute, ...moviePopupRoute];

@NgModule({
  imports: [BeanangularSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MovieComponent, MovieDetailComponent, MovieUpdateComponent, MovieDeleteDialogComponent, MovieDeletePopupComponent],
  entryComponents: [MovieComponent, MovieUpdateComponent, MovieDeleteDialogComponent, MovieDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BeanangularMovieModule {}
