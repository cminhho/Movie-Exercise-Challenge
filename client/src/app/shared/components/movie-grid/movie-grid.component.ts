import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-movie-grid',
  templateUrl: './movie-grid.component.html',
  styleUrls: ['./movie-grid.component.css']
})
export class MovieGridComponent implements OnInit {

  @Input()
  movies: any;

  constructor() { }

  ngOnInit() {
  }

}
