import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';

import { IMovie } from 'app/shared/model/movie.model';

import { MovieService } from '../movie-management.service';
import { TmaEventManager } from '@app/core';
import { ToastService } from '@app/shared';

const ITEMS_PER_PAGE = 10;
@Component({
  selector: 'app-movie-management',
  templateUrl: './movie-management.component.html'
})
export class MovieComponent implements OnInit, OnDestroy {
  movies: IMovie[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  totalPages: number;
  isPopulatingLoading: boolean;

  constructor(
    protected movieService: MovieService,
    protected eventManager: TmaEventManager,
    protected toastService: ToastService
  ) {
    this.movies = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.totalPages = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
    this.isPopulatingLoading = false;
  }

  loadAll() {
    this.movieService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
        eagerload: true
      })
      .subscribe(
        (res: HttpResponse<IMovie[]>) => this.paginateMovies(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
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

  ngOnInit() {
    this.loadAll();
    this.registerChangeInMovies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMovie) {
    return item.id;
  }

  registerChangeInMovies() {
    this.eventSubscriber = this.eventManager.subscribe('movieListModification', (response: any) => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  populateMovieData() {
    this.isPopulatingLoading = true;
    this.movieService
      .populateMovieData({
        listType: 'popular',
        fromPage: 1,
        toPage: 5
      })
      .subscribe(
        (res: HttpResponse<any>) => {
          this.isPopulatingLoading = false;
          const responseMessage = res.body.totalCrawledItems + ' movie records are added successfullly';
          this.toastService.show(responseMessage, { classname: 'bg-primary text-light', delay: 10000 });
          this.loadAll();
        },
        (res: HttpErrorResponse) => {
          this.toastService.show('Error while populating movie records');
          this.isPopulatingLoading = false;
        }
      );
  }

  protected paginateMovies(data: any, headers: HttpHeaders) {
    this.totalItems = data.totalElements;
    this.totalPages = data.totalPages;
    this.movies.concat(data.results);
  }

  protected onError(errorMessage: string) {
    // display error message
  }
}
