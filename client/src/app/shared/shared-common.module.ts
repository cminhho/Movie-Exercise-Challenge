import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedLibsModule } from './shared-libs.modules';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MovieBannerComponent } from './components/movie-banner/movie-banner.component';
import { RatingComponent } from './components/rating/rating.component';
import { LoadingComponent } from './components/loading/loading.component';
import { TabsetComponent } from './components/tabset/tabset.component';
import { MovieGridComponent } from './components/movie-grid/movie-grid.component';
import { MovieGridItemComponent } from './components/movie-grid/movie-grid-item/movie-grid-item.component';
import { DisplayByDirective } from './directives/displayby.directive';
import { DisplayDirective } from './directives/display.directive';
import { ToastsContainerComponent } from './components/toasts-container/toasts-container.component';

@NgModule({
  imports: [SharedLibsModule, NgbModule],
  declarations: [
    MovieBannerComponent,
    RatingComponent,
    LoadingComponent,
    TabsetComponent,
    DisplayByDirective,
    DisplayDirective,
    MovieGridComponent,
    MovieGridItemComponent,
    ToastsContainerComponent
  ],
  exports: [
    MovieBannerComponent,
    RatingComponent,
    LoadingComponent,
    TabsetComponent,
    DisplayByDirective,
    DisplayDirective,
    MovieGridComponent,
    MovieGridItemComponent,
    ToastsContainerComponent
  ],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SharedCommonModule {}
