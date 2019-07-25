import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-movie-grid-item',
  templateUrl: './movie-grid-item.component.html',
  styleUrls: ['./movie-grid-item.component.css']
})
export class MovieGridItemComponent implements OnInit {
  @Input()
  movie: any;
  constructor() { }

  ngOnInit() {
  }

}
