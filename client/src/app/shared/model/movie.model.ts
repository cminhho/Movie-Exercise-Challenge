import { IComment } from 'app/shared/model/comment.model';
import { IGenre } from 'app/shared/model/genre.model';
import { IList } from 'app/shared/model/list.model';

export const enum MediaType {
  MOVIE = 'MOVIE',
  SONG = 'SONG'
}

export const enum Language {
  FRENCH = 'FRENCH',
  ENGLISH = 'ENGLISH',
  SPANISH = 'SPANISH'
}

export interface IMovie {
  id?: number;
  title?: string;
  voteAverage?: number;
  voteCount?: number;
  video?: boolean;
  mediaType?: MediaType;
  popularity?: number;
  posterPath?: string;
  originalLanguage?: Language;
  originalTitle?: string;
  backdropPath?: string;
  adult?: boolean;
  overview?: string;
  releaseDate?: string;
  comments?: IComment[];
  genres?: IGenre[];
  list?: IList;
}

export class Movie implements IMovie {
  constructor(
    public id?: number,
    public title?: string,
    public voteAverage?: number,
    public voteCount?: number,
    public video?: boolean,
    public mediaType?: MediaType,
    public popularity?: number,
    public posterPath?: string,
    public originalLanguage?: Language,
    public originalTitle?: string,
    public backdropPath?: string,
    public adult?: boolean,
    public overview?: string,
    public releaseDate?: string,
    public comments?: IComment[],
    public genres?: IGenre[],
    public list?: IList
  ) {
    this.video = this.video || false;
    this.adult = this.adult || false;
  }
}
