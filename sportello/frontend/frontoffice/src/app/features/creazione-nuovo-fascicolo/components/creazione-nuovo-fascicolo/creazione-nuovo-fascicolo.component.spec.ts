import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreazioneNuovoFascicoloComponent } from './creazione-nuovo-fascicolo.component';

describe('CreazioneNuovoFascicoloComponent', () => {
  let component: CreazioneNuovoFascicoloComponent;
  let fixture: ComponentFixture<CreazioneNuovoFascicoloComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreazioneNuovoFascicoloComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreazioneNuovoFascicoloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
