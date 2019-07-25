import { Directive, EventEmitter, Output, Input, OnInit, Renderer2, ElementRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { DisplayTabService } from './display-tab.service';

@Directive({
  selector: '[appDisplay]'
})

export class DisplayDirective implements OnInit {
  @Input() tab: string;
  @Output()
  displayed = new EventEmitter();
  private columnSortedSubscription: Subscription;
  constructor(
    private displayTabService: DisplayTabService,
    private renderer: Renderer2,
    private el: ElementRef
  ) { }

  ngOnInit() {
    console.log(this.renderer);
    console.log(this.el.nativeElement);
    this.columnSortedSubscription = this.displayTabService.columnDisplayed$
      .subscribe(event => {
        this.displayed.emit(event);
    });
  }
}
