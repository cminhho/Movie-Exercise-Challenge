import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AdminLayoutComponent } from '@app/layouts';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'movie-management',
        component: AdminLayoutComponent,
        loadChildren: './movie-management/movie-management.module#MovieMgmtModule'
      }
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminModule {}
