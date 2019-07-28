import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedLibsModule } from './shared-libs.modules';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CarouselComponent } from './components/carousel/carousel.component';
import { RatingComponent } from './components/rating/rating.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { TabsetComponent } from './components/tabset/tabset.component';
import { MovieGridComponent } from './components/movie-grid/movie-grid.component';
import { MovieGridItemComponent } from './components/movie-grid-item/movie-grid-item.component';
import { DisplayByDirective } from './directives/displayby.directive';
import { DisplayDirective } from './directives/display.directive';

@NgModule({
  imports: [SharedLibsModule, NgbModule],
  declarations: [
    CarouselComponent,
    RatingComponent,
    PaginationComponent,
    TabsetComponent,
    DisplayByDirective,
    DisplayDirective,
    MovieGridComponent,
    MovieGridItemComponent
  ],
  exports: [
    CarouselComponent,
    RatingComponent,
    PaginationComponent,
    TabsetComponent,
    DisplayByDirective,
    DisplayDirective,
    MovieGridComponent,
    MovieGridItemComponent
  ],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SharedCommonModule {}
