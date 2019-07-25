import { Directive, HostListener, Input, OnInit, Renderer2, ElementRef } from '@angular/core';
import { DisplayTabService } from './display-tab.service';

@Directive({
  selector: '[appDisplayBy]'
})
export class DisplayByDirective {
  @Input('appDisplayBy')
  tabName: string;

  constructor(private displayTabService: DisplayTabService, private renderer: Renderer2, private el: ElementRef) { }

  @HostListener('click')
  display() {
    console.log(this.renderer);
    console.log(this.el.nativeElement);
    this.renderer.addClass(this.el.nativeElement.children[0], 'active');

    this.displayTabService.columnDisplayed({
      tabName: this.tabName});
  }
}
