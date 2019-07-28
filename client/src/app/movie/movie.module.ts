import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MovieComponent } from './list/movie.component';
import { movieRoute } from './movie.route';
import { RouterModule } from '@angular/router';
import { SharedModule } from '@app/shared';

const MOVIE_STATES = [...movieRoute];

@NgModule({
  declarations: [MovieComponent],
  imports: [
    SharedModule,
    RouterModule.forChild(MOVIE_STATES)
  ],
  entryComponents: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieModule { }
