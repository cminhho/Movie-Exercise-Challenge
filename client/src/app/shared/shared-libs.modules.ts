import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

@NgModule({
  imports: [NgbModule, FormsModule],
  exports: [
    NgbModule,
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    AngularFontAwesomeModule,
    InfiniteScrollModule
  ],
  entryComponents: [],
  providers: []
})
export class SharedLibsModule {}
