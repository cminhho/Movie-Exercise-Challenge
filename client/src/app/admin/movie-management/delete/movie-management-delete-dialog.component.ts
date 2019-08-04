import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from '../movie-management.service';
import { TmaEventManager } from '@app/core';

@Component({
  selector: 'app-movie-management-delete-dialog',
  templateUrl: './movie-management-delete-dialog.component.html'
})
export class MovieDeleteDialogComponent {
  movie: IMovie;

  constructor(protected movieService: MovieService, public activeModal: NgbActiveModal, protected eventManager: TmaEventManager) { }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.movieService.deleteById(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'movieListModification',
        content: 'Deleted an movie'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'app-movie-delete-popup',
  template: ''
})
export class MovieDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) { }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ movie }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MovieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.movie = movie;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/movie-management', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/movie-management', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
