import { IMovie } from 'app/shared/model/movie.model';

export interface IList {
  id?: number;
  title?: string;
  description?: string;
  backdropPath?: string;
  posterPath?: string;
  movies?: IMovie[];
}

export class List implements IList {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public backdropPath?: string,
    public posterPath?: string,
    public movies?: IMovie[]
  ) {}
}
