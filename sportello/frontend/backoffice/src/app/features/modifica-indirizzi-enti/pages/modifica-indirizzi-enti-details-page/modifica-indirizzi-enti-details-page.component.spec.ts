import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificaIndirizziEntiDetailsPageComponent } from './modifica-indirizzi-enti-details-page.component';

describe('ModificaIndirizziEntiDetailsPageComponent', () => {
  let component: ModificaIndirizziEntiDetailsPageComponent;
  let fixture: ComponentFixture<ModificaIndirizziEntiDetailsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModificaIndirizziEntiDetailsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModificaIndirizziEntiDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
