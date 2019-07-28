import { Component, OnInit, Input } from '@angular/core';
import { IMovie } from '@app/shared/model/movie.model';

const DEFAULT_WATCH_NOW_TITLE = 'WATCH MOVIE';
@Component({
  selector: 'app-movie-grid-item',
  templateUrl: './movie-grid-item.component.html',
  styleUrls: ['./movie-grid-item.component.scss']
})
export class MovieGridItemComponent implements OnInit {
  @Input()
  movie: IMovie;

  watchMovieTitle = DEFAULT_WATCH_NOW_TITLE;
  private baseImageUrl = 'https://image.tmdb.org/t/p/w500/';
  private DEFAULT_IMAGEURL = 'http://placehold.it/265x375';

  constructor() { }

  ngOnInit() {
  }

  createPosterImageUrl(posterPath: string) {
    if (!posterPath) {
      return this.DEFAULT_IMAGEURL;
    }
    return this.baseImageUrl + posterPath;
  }

  calculateVoteAverage(voteAverage: number) {
    return voteAverage / 2;
  }

}
