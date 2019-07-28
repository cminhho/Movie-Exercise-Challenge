/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { MovieService } from 'app/movie/movie.service';
import { IMovie, Movie, MediaType, Language } from 'app/shared/model/movie.model';

describe('Service Tests', () => {
  describe('Movie Service', () => {
    let injector: TestBed;
    let service: MovieService;
    let httpMock: HttpTestingController;
    let elemDefault: IMovie;
    let expectedResult: any;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MovieService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Movie(
        0,
        'title',
        0,
        0,
        false,
        MediaType.MOVIE,
        0,
        'posterPath',
        Language.FRENCH,
        'originalTitle',
        'backdropPath',
        false,
        'overview',
        'releaseDate'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .findById(123)
          .pipe(take(1))
          .subscribe((resp: any) => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toBe({ body: elemDefault });
      });


    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
