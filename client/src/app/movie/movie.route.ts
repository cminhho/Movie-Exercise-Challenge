import {
  Routes
} from '@angular/router';
import { MovieComponent } from './list/movie.component';

export const movieRoute: Routes = [
  {
    path: '',
    component: MovieComponent,
    resolve: {},
    children: [
      {
        path: 'popular',
        component: MovieComponent,
      },
      {
        path: 'top_rated',
        component: MovieComponent,
      },
      {
        path: 'upcoming',
        component: MovieComponent,
      },
      {
        path: 'genre',
        component: MovieComponent,
      },
    ]
  },
];
