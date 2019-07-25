import { Injectable, Renderer2, ElementRef } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DisplayTabService {
  private columnDisplayedSource = new Subject<any>();
  public columnDisplayed$ = this.columnDisplayedSource.asObservable();

  constructor() { }

  columnDisplayed(event: any) {
    this.columnDisplayedSource.next(event);
  }
}
