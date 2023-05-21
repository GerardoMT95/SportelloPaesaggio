import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TecnicoIncaricatoComponent } from './tecnico-incaricato.component';

describe('TecnicoIncaricatoComponent', () => {
  let component: TecnicoIncaricatoComponent;
  let fixture: ComponentFixture<TecnicoIncaricatoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TecnicoIncaricatoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TecnicoIncaricatoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
