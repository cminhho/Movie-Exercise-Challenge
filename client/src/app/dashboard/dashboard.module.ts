import { CommonModule } from '@angular/common';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { DashboardRoutes } from '@app/dashboard/dashboard.routing';
import { DashboardComponent } from '@app/dashboard/dashboard/dashboard.component';
import { SharedModule } from '@app/shared';

@NgModule({
  imports: [
    SharedModule,
    RouterModule.forChild(DashboardRoutes)
  ],
  declarations: [DashboardComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardModule {}
