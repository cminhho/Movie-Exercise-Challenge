import { Component, OnInit, Input } from '@angular/core';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { IMovie } from '@app/shared/model/movie.model';

@Component({
  selector: 'movie-banner',
  templateUrl: './movie-banner.component.html',
  styleUrls: ['./movie-banner.component.scss']
})
export class MovieBannerComponent implements OnInit {
  @Input()
  movies: IMovie[];

  baseImageUrl = 'https://image.tmdb.org/t/p/w500/';
  DEFAULT_IMAGEURL = 'http://placehold.it/1905x480';

  constructor(
    private carouselConfig: NgbCarouselConfig
  ) { }

  ngOnInit() {
    this.carouselConfig.interval = 3000;
    this.carouselConfig.showNavigationArrows = false;
    this.carouselConfig.wrap = false;
    this.carouselConfig.keyboard = false;
    this.carouselConfig.pauseOnHover = false;
  }

  createBackdropImageUrl(backdropPath: string) {
    if (!backdropPath) {
      return this.DEFAULT_IMAGEURL;
    }
    return this.baseImageUrl + backdropPath;
  }
}
