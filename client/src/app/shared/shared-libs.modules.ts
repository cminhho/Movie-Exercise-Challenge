import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  imports: [
    NgbModule,
    FormsModule,
    InfiniteScrollModule,
    FontAwesomeModule
  ],
  exports: [
    NgbModule,
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    InfiniteScrollModule,
    FontAwesomeModule
  ],
  entryComponents: [],
  providers: []
})
export class SharedLibsModule {}
