import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CensimentoOrganizzazioneComponent } from './censimento-organizzazione.component';

describe('CensimentoOrganizzazioneComponent', () => {
  let component: CensimentoOrganizzazioneComponent;
  let fixture: ComponentFixture<CensimentoOrganizzazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CensimentoOrganizzazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CensimentoOrganizzazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
