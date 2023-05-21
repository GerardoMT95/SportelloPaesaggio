import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComunicazioniTableComponent } from './comunicazioni-table.component';

describe('ComunicazioniTableComponent', () => {
  let component: ComunicazioniTableComponent;
  let fixture: ComponentFixture<ComunicazioniTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComunicazioniTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComunicazioniTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
