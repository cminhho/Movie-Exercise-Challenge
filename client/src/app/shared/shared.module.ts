import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedLibsModule } from './shared-libs.modules';
import { SharedCommonModule } from './shared-common.module';
import { CommonModule } from '@angular/common';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FormsModule } from '@angular/forms';
@NgModule({
  imports: [SharedLibsModule, SharedCommonModule],
  declarations: [],
  exports: [
    FormsModule,
    CommonModule,
    SharedLibsModule,
    SharedCommonModule,
    InfiniteScrollModule
  ],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SharedModule {}
