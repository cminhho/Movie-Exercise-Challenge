import { Component, OnInit } from '@angular/core';
import { NgbTabsetConfig } from '@ng-bootstrap/ng-bootstrap';
import { MovieService } from '../movie.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {
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
  movies = [
    {
      title: 'WRATH OF THE TITANS',
      image: 'https://livedemo00.template-help.com/wt_prod-20691/images/gallery-1.jpg',
      category: ['Fantasy', 'Animation', 'Family'],
      duration: '1h52',
      currentRate: '3.14',
      reviews: '4343',
    },
    {
      title: 'WRATH OF THE TITANS',
      image: 'https://livedemo00.template-help.com/wt_prod-20691/images/gallery-1.jpg',
      category: ['Action', 'Thriller'],
      duration: '1h32',
      currentRate: '3.12',
      reviews: '1234',
    },
    {
      title: 'WRATH OF THE TITANS',
      image: 'https://livedemo00.template-help.com/wt_prod-20691/images/gallery-1.jpg',
      category: ['Action', 'Adventure', 'Fantasy','test','Test'],
      duration: '1h00',
      currentRate: '3.77',
      reviews: '4564'
    },
    {
      title: 'WRATH OF THE TITANS',
      image: 'https://livedemo00.template-help.com/wt_prod-20691/images/gallery-1.jpg',
      category: ['Action', 'Adventure', 'Fantasy'],
      duration: '1h00',
      currentRate: '3.77',
      reviews: '7875',
    },
    {
      title: 'WRATH OF THE TITANS',
      image: 'https://livedemo00.template-help.com/wt_prod-20691/images/gallery-1.jpg',
      category: ['Action', 'Adventure', 'Fantasy'],
      duration: '1h00',
      currentRate: '3.77',
      reviews: '4664',
    },
  ];

  moviesForTopRated = [
    {
      title: 'WRATH OF THE TITANS',
      image: 'https://livedemo00.template-help.com/wt_prod-20691/images/gallery-1.jpg',
      category: ['Fantasy', 'Animation', 'Family'],
      duration: '1h52',
      currentRate: '3.14',
      reviews: '4343',
    },
    {
      title: 'WRATH OF THE TITANS',
      image: 'https://livedemo00.template-help.com/wt_prod-20691/images/gallery-1.jpg',
      category: ['Action', 'Thriller'],
      duration: '1h32',
      currentRate: '3.12',
      reviews: '1234',
    },
    {
      title: 'WRATH OF THE TITANS',
      image: 'https://livedemo00.template-help.com/wt_prod-20691/images/gallery-1.jpg',
      category: ['Action', 'Adventure', 'Fantasy'],
      duration: '1h00',
      currentRate: '3.77',
      reviews: '4564'
    },
    {
      title: 'WRATH OF THE TITANS',
      image: 'https://livedemo00.template-help.com/wt_prod-20691/images/gallery-1.jpg',
      category: ['Action', 'Adventure', 'Fantasy'],
      duration: '1h00',
      currentRate: '3.77',
      reviews: '7875',
    }
  ];

  constructor(
    private config: NgbTabsetConfig,
    private movieService: MovieService,
    ) { }

  ngOnInit() {
    this.loadAll();
  }

  onChangedTab(event: any) {
    console.log(event);
    this.movies = this.moviesForTopRated;
  }

  private loadAll() {
    this.movieService.query().subscribe((res: HttpResponse<any[]>) => {
      // this.paginateCrawlRequests(res.body.results);
    }, (error: any) => {

    });
  }

  private paginateCrawlRequests(data: any) {
    this.movies = data;
  }
}
