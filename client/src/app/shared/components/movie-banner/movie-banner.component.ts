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
  emptyImageUrl = 'http://cdn.collider.com/wp-content/uploads/dark-knight-rises-movie-poster-banner-batman.jpg';

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
      return this.emptyImageUrl;
    }
    return this.baseImageUrl + backdropPath;
  }
}
