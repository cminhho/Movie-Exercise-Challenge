import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {
  today: number;
  copyrightNotice: string;

  constructor() {}

  ngOnInit() {
    this.today = Date.now();
    this.copyrightNotice =
      'All Rights Reserved. The term "Tma" refers to Tma Inc. and/or its subsidiaries.';
  }
}
