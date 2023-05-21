import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IstanzeSearchComponent } from './istanze-search.component';

describe('IstanzeSearchComponent', () => {
  let component: IstanzeSearchComponent;
  let fixture: ComponentFixture<IstanzeSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IstanzeSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IstanzeSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
