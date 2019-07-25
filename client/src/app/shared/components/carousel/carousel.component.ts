import { Component, OnInit, Input } from '@angular/core';
import { NgbCarouselConfig, NgbTabsetConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnInit {
  @Input()
  movies: any;

  constructor(
    private carouselConfig: NgbCarouselConfig,
    private tabsetConfig: NgbTabsetConfig,
  ) { }

  ngOnInit() {
    // customize default values of carousels used by this component tree
    this.carouselConfig.interval = 1000000000000000;
    this.carouselConfig.wrap = false;
    this.carouselConfig.keyboard = false;
    this.carouselConfig.pauseOnHover = false;
  }

}
