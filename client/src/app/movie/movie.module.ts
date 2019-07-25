import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovieComponent } from './list/movie.component';
import { movieRoute } from './movie.route';
import { RouterModule } from '@angular/router';
import { SharedModule } from '@app/shared';
import { MovieGridComponent } from '../shared/components/movie-grid/movie-grid.component';
import { MovieGridItemComponent } from '../shared/components/movie-grid-item/movie-grid-item.component';

const ENTITY_STATES = [...movieRoute];

@NgModule({
  declarations: [MovieComponent, MovieGridComponent, MovieGridItemComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(ENTITY_STATES),
    SharedModule
  ],
  entryComponents: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieModule { }
