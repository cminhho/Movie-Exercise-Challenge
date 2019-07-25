import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  // beforeEach(async(() => {
  //   TestBed.configureTestingModule({
  //     declarations: [
  //       AppComponent
  //     ],
  //   }).compileComponents();
  // }));

  it('true is true', () => {
    expect(true).toEqual(true);
    // expect(false).toEqual(true);
  });
  // it('should create the app', () => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   const app = fixture.debugElement.componentInstance;
  //   expect(app).toBeTruthy();
  // });
  // it(`should have as title 'Tma Application'`, async(() => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   const app = fixture.debugElement.componentInstance;
  //   expect(app.title).toEqual('Tma Application');
  // }));
  // it('should render title in a h1 tag', async(() => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   fixture.detectChanges();
  //   const compiled = fixture.debugElement.nativeElement;
  //   console.log();

  //   expect(compiled.querySelector('h1').textContent).toContain('Welcome to a!');
  // }));
});

describe('1st test', () => {
  it('true is true', () => {
    expect(true).toEqual(true);
    // expect(false).toEqual(true);
  });
});
