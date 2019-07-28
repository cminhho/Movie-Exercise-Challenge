import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMovie, Movie } from 'app/shared/model/movie.model';
import { MovieService } from '../movie-management.service';
import { IGenre } from 'app/shared/model/genre.model';
import { IList } from 'app/shared/model/list.model';

@Component({
  selector: 'app-movie-update',
  templateUrl: './movie-management-update.component.html'
})
export class MovieUpdateComponent implements OnInit {
  isSaving: boolean;

  genres: IGenre[];

  lists: IList[];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    voteAverage: [],
    voteCount: [],
    video: [],
    mediaType: [],
    popularity: [],
    posterPath: [],
    originalLanguage: [],
    originalTitle: [],
    backdropPath: [],
    adult: [],
    overview: [],
    releaseDate: [],
    genres: [],
    list: []
  });

  constructor(
    protected movieService: MovieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ movie }) => {
      this.updateForm(movie);
    });
  }

  updateForm(movie: IMovie) {
    this.editForm.patchValue({
      id: movie.id,
      title: movie.title,
      voteAverage: movie.voteAverage,
      voteCount: movie.voteCount,
      video: movie.video,
      mediaType: movie.mediaType,
      popularity: movie.popularity,
      posterPath: movie.posterPath,
      originalLanguage: movie.originalLanguage,
      originalTitle: movie.originalTitle,
      backdropPath: movie.backdropPath,
      adult: movie.adult,
      overview: movie.overview,
      releaseDate: movie.releaseDate,
      genres: movie.genres,
      list: movie.list
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const movie = this.createFromForm();
    if (movie.id !== undefined) {
      this.subscribeToSaveResponse(this.movieService.update(movie));
    } else {
      this.subscribeToSaveResponse(this.movieService.create(movie));
    }
  }

  private createFromForm(): IMovie {
    return {
      ...new Movie(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      voteAverage: this.editForm.get(['voteAverage']).value,
      voteCount: this.editForm.get(['voteCount']).value,
      video: this.editForm.get(['video']).value,
      mediaType: this.editForm.get(['mediaType']).value,
      popularity: this.editForm.get(['popularity']).value,
      posterPath: this.editForm.get(['posterPath']).value,
      originalLanguage: this.editForm.get(['originalLanguage']).value,
      originalTitle: this.editForm.get(['originalTitle']).value,
      backdropPath: this.editForm.get(['backdropPath']).value,
      adult: this.editForm.get(['adult']).value,
      overview: this.editForm.get(['overview']).value,
      releaseDate: this.editForm.get(['releaseDate']).value,
      genres: this.editForm.get(['genres']).value,
      list: this.editForm.get(['list']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovie>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    // display error message
  }

  trackGenreById(index: number, item: IGenre) {
    return item.id;
  }

  trackListById(index: number, item: IList) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
