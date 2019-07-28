import { Component, OnInit, Input } from '@angular/core';
import { IMovie } from '@app/shared/model/movie.model';

@Component({
  selector: 'app-movie-grid-item',
  templateUrl: './movie-grid-item.component.html',
  styleUrls: ['./movie-grid-item.component.scss']
})
export class MovieGridItemComponent implements OnInit {
  @Input()
  movie: IMovie;

  watchMovieTitle = 'WATCH MOVIE';
  baseImageUrl = 'https://image.tmdb.org/t/p/w500/';
  emptyImageUrl = 'https://cdn1.iconfinder.com/data/icons/business-company-1/500/image-512.png';

  constructor() { }

  ngOnInit() {
  }

  createPosterImageUrl(posterPath: string) {
    if (!posterPath) {
      return this.emptyImageUrl;
    }
    return this.baseImageUrl + posterPath;
  }

  calculateVoteAverage(voteAverage: number) {
    return voteAverage / 2;
  }

}
