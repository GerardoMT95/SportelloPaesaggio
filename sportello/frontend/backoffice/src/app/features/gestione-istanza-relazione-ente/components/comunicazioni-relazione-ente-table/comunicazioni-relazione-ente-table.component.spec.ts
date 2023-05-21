import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComunicazioniRelazioneEnteTableComponent } from './comunicazioni-relazione-ente-table.component';

describe('ComunicazioniTableComponent', () => {
  let component: ComunicazioniRelazioneEnteTableComponent;
  let fixture: ComponentFixture<ComunicazioniRelazioneEnteTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComunicazioniRelazioneEnteTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComunicazioniRelazioneEnteTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
