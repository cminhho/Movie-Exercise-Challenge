import { Component, Input, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map, mergeMap } from 'rxjs/operators';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.scss']
})
export class AdminHeaderComponent implements OnInit {
  @Input()
  layout: any;
  pageInfo: any;
  version: 'v1.0.0';
  public isNavbarCollapsed = true;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private titleService: Title
  ) {
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd),
        map(() => this.activatedRoute),
        map(route => {
          while (route.firstChild) {
            route = route.firstChild;
          }
          return route;
        }),
        filter(route => route.outlet === 'primary'),
        mergeMap(route => route.data)
      )
      .subscribe(event => {
        this.titleService.setTitle(event['title']);
        this.pageInfo = event;
      });
  }

  ngOnInit() {
    window.addEventListener('scroll', this.scroll, true); //third parameter
    // $(document).ready(function () {
    //   // Transition effect for navbar
    //   $(window).scroll(function () {
    //     // checks if window is scrolled more than 300px, adds/removes solid class
    //     if ($(this).scrollTop() > 100) {
    //       $('.navbar').addClass('solid');
    //     } else {
    //       $('.navbar').removeClass('solid');
    //     }
    //   });
    // });
  }

  scroll = (): void => {
    if ($(this).scrollTop() > 100) {
      $('.navbar').addClass('solid');
    } else {
      $('.navbar').removeClass('solid');
    }
  };
}
