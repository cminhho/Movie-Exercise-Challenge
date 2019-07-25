import { Component, OnInit, EventEmitter, Output, Renderer2, ElementRef } from '@angular/core';

@Component({
  selector: 'app-tabset',
  templateUrl: './tabset.component.html',
  styleUrls: ['./tabset.component.css']
})
export class TabsetComponent implements OnInit {
  @Output()
  changedTab = new EventEmitter();
  choseTag: boolean;

  activeTab: string;

  constructor(
  ) { }

  ngOnInit() {
    this.activeTab = "popular";
  }

  // onDisplayed(event: any) {
  //   this.changedTab.emit(event);
  // }

  onChangedTab(tabChanged: any) {
    if (this.activeTab !== tabChanged) {
      this.activeTab = tabChanged;
    }
    this.changedTab.emit(tabChanged);
  }
}
