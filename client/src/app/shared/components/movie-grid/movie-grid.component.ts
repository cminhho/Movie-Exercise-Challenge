import { Component, OnInit, Input } from '@angular/core';
import { IMovie } from '@app/shared/model/movie.model';

@Component({
  selector: 'app-movie-grid',
  templateUrl: './movie-grid.component.html',
  styleUrls: ['./movie-grid.component.css']
})
export class MovieGridComponent implements OnInit {

  @Input()
  movies: IMovie[];

  constructor() { }

  ngOnInit() {
  }

}
