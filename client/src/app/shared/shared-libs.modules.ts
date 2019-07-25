import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { NgSelectModule } from '@ng-select/ng-select';
import { TreeModule } from 'ng2-tree';
@NgModule({
  imports: [NgbModule, NgSelectModule, FormsModule],
  exports: [
    NgbModule,
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    AngularFontAwesomeModule,
    NgSelectModule,
    TreeModule
  ],
  entryComponents: [],
  providers: []
})
export class SharedLibsModule {}
