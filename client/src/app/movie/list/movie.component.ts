import { Component, OnInit } from '@angular/core';
import { NgbTabsetConfig } from '@ng-bootstrap/ng-bootstrap';
import { MovieService } from '../movie.service';
import { HttpResponse } from '@angular/common/http';

const ITEMS_PER_PAGE = 20;

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {
  movies: any[];
  currentAccount: any;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  selectedMoveType: string;

  carouselMovies = [
    {
      title: 'WRATH OF THE TITANS',
      image: '/assets/images/Bitmap.png',
      category: ['Fantasy', 'Animation', 'Family'],
      duration: '1h52',
      currentRate: '3.14',
      reviews: '4343',
    },
    {
      title: 'WRATH OF THE TITANS',
      image: '/assets/images/Bitmap.png',
      category: ['Action', 'Thriller'],
      duration: '1h32',
      currentRate: '3.12',
      reviews: '1234',
    }
  ];

  constructor(
    private config: NgbTabsetConfig,
    private movieService: MovieService,
  ) { }

  ngOnInit() {
    this.movies = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 1;
    this.selectedMoveType = 'popular';
    this.loadAll();
  }

  onChangedTab(selectedMoveType: string) {
    this.selectedMoveType = selectedMoveType;
    this.reset();
  }

  reset() {
    this.page = 1;
    this.movies = [];
    this.loadAll();
  }

  loadPage(page: any) {
    this.page = page;
    this.loadAll();
  }

  private loadAll() {
    this.movieService.query(this.selectedMoveType, {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort()
    }).subscribe((res: HttpResponse<any[]>) => {
      this.paginateCrawlRequests(res.body);
    }, (error: any) => {
    });
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private paginateCrawlRequests(data: any) {
    for (let i = 0; i < data.results.length; i++) {
      this.movies.push(data.results[i]);
    }
  }
}
