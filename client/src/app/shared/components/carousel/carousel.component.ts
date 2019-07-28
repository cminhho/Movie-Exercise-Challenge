import { Component, OnInit, Input } from '@angular/core';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.scss']
})
export class CarouselComponent implements OnInit {
  @Input()
  movies: any;

  constructor(
    private carouselConfig: NgbCarouselConfig
  ) { }

  ngOnInit() {
    this.carouselConfig.interval = 1000;
    this.carouselConfig.showNavigationArrows = false;
    this.carouselConfig.wrap = false;
    this.carouselConfig.keyboard = false;
    this.carouselConfig.pauseOnHover = false;
  }

}
