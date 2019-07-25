import { Routes } from '@angular/router';

import { BlankComponent, FullComponent } from '@app/shared';

export const AppRoutes: Routes = [
  {
    path: '',
    component: FullComponent,
    canActivate: [],
    children: [
      {
        path: '',
        redirectTo: '/movie',
        pathMatch: 'full'
      },
      {
        path: 'movie',
        loadChildren: './movie/movie.module#MovieModule'
      }
    ]
  },
  {
    path: '**',
    redirectTo: '/404'
  }
];
