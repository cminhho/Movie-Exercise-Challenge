import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedLibsModule } from './shared-libs.modules';
import { SharedCommonModule } from './shared-common.module';
@NgModule({
  imports: [SharedLibsModule, SharedCommonModule],
  declarations: [],
  exports: [SharedLibsModule, SharedCommonModule],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SharedModule {}
