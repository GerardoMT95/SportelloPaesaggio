import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificaIndirizziEntiPageComponent } from './modifica-indirizzi-enti-page.component';

describe('ModificaIndirizziEntiPageComponent', () => {
  let component: ModificaIndirizziEntiPageComponent;
  let fixture: ComponentFixture<ModificaIndirizziEntiPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModificaIndirizziEntiPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModificaIndirizziEntiPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
