import * as $ from 'jquery';
import './vendor.ts';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutes } from '@app/app.routing';
import { AppComponent } from '@app/app.component';
import { CoreModule } from '@app/core';
import { SharedModule } from '@app/shared';
import { AdminModule } from '@app/admin/admin.module';

import {
  HeaderComponent,
  BlankComponent,
  FullComponent,
  FooterComponent,
  AdminLayoutComponent,
  AdminHeaderComponent
} from './layouts';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BlankComponent,
    FullComponent,
    FooterComponent,
    AdminLayoutComponent,
    AdminHeaderComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgbModule,
    RouterModule.forRoot(AppRoutes, {
      useHash: true
    }),
    CoreModule,
    SharedModule,
    AdminModule
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {}
