import { IMovie } from 'app/shared/model/movie.model';

export interface IGenre {
  id?: number;
  title?: string;
  updatedAt?: string;
  movies?: IMovie[];
}

export class Genre implements IGenre {
  constructor(public id?: number, public title?: string, public updatedAt?: string, public movies?: IMovie[]) {}
}
