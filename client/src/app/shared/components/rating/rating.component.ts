import { Component, OnInit, Input } from '@angular/core';

@Component({
  // tslint:disable-next-line: component-selector
  selector: 'movie-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent implements OnInit {
  @Input()
  currentRate: number;
  @Input()
  reviews: number;
  constructor() { }

  ngOnInit() {
  }

}
