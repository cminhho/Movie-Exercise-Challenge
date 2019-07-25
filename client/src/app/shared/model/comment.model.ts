import { IMovie } from 'app/shared/model/movie.model';

export interface IComment {
  id?: number;
  review?: string;
  movie?: IMovie;
}

export class Comment implements IComment {
  constructor(public id?: number, public review?: string, public movie?: IMovie) {}
}
