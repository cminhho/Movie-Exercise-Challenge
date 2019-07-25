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
        path: 'toprated',
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