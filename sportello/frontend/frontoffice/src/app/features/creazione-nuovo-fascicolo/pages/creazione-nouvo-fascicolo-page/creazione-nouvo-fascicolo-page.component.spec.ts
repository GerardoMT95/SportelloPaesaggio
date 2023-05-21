import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreazioneNouvoFascicoloPageComponent } from './creazione-nouvo-fascicolo-page.component';

describe('CreazioneNouvoFascicoloPageComponent', () => {
  let component: CreazioneNouvoFascicoloPageComponent;
  let fixture: ComponentFixture<CreazioneNouvoFascicoloPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreazioneNouvoFascicoloPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreazioneNouvoFascicoloPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
