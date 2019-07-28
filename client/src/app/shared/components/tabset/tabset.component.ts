import { Component, OnInit, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-tabset',
  templateUrl: './tabset.component.html',
  styleUrls: ['./tabset.component.scss']
})
export class TabsetComponent implements OnInit {
  @Output()
  changedTab = new EventEmitter();
  choseTag: boolean;
  items: any[] = [];

  activeTab: string;

  constructor(
  ) { }

  ngOnInit() {
    this.items = [{
      title: 'Popular',
      value: 'popular'
    }, {
      title: 'Top Rated',
      value: 'top_rated'
    }, {
      title: 'Upcoming',
      value: 'upcoming'
    }];
    this.activeTab = 'popular';
  }

  onChangedTab(tabChanged: any) {
    if (this.activeTab !== tabChanged) {
      this.activeTab = tabChanged;
    }
    this.changedTab.emit(tabChanged);
  }
}
