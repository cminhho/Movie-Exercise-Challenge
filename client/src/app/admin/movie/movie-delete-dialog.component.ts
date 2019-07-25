import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from './movie.service';

@Component({
  selector: 'jhi-movie-delete-dialog',
  templateUrl: './movie-delete-dialog.component.html'
})
export class MovieDeleteDialogComponent {
  movie: IMovie;

  constructor(protected movieService: MovieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.movieService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'movieListModification',
        content: 'Deleted an movie'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-movie-delete-popup',
  template: ''
})
export class MovieDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ movie }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MovieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.movie = movie;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/movie', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/movie', { outlets: { popup: null } }]);
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
