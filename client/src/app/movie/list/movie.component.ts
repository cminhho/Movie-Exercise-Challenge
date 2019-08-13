import { Component, OnInit } from '@angular/core';
import { MovieService } from '../movie.service';
import { HttpResponse } from '@angular/common/http';
import { IMovie } from '@app/shared/model/movie.model';

const ITEMS_PER_PAGE = 20;
const DEFAULT_MOVIE_LIST = 'popular';

const DEFAULT_MOVIE_BANNER_ITEMS = 3;
const DEFAULT_MOVIE_BANNER_LIST = 'popular';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss']
})
export class MovieComponent implements OnInit {
  currentAccount: any;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalPages: number;
  selectedMoveType: string;

  movies: IMovie[];
  bannerMovies: IMovie[];

  constructor(
    private movieService: MovieService,
  ) { }

  ngOnInit() {
    this.bannerMovies = [];
    this.movies = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.selectedMoveType = DEFAULT_MOVIE_LIST;
    this.loadBannerMovies();
    this.loadAll();
  }

  onChangedTab(selectedMoveType: string) {
    this.selectedMoveType = selectedMoveType;
    this.reset();
  }

  reset() {
    this.page = 0;
    this.movies = [];
    this.loadAll();
  }

  loadPage(page: any) {
    this.page = page;
    this.loadAll();
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private loadBannerMovies() {
    this.movieService.query(DEFAULT_MOVIE_BANNER_LIST, {
      page: 0,
      size: DEFAULT_MOVIE_BANNER_ITEMS,
      sort: this.sort()
    }).subscribe((res: HttpResponse<any[]>) => {
      this.buildBannerMovies(res.body);
    }, (error: any) => {
    });
  }

  private buildBannerMovies(body: any) {
    this.bannerMovies = body.results;
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

  private paginateCrawlRequests(data: any) {
    this.totalPages = data.totalPages;
    this.movies.concat(data.results);
  }
}
