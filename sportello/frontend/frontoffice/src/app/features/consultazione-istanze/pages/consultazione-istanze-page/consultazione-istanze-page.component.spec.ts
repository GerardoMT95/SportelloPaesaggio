import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultazioneIstanzePageComponent } from './consultazione-istanze-page.component';

describe('ConsultazioneIstanzeComponent', () => {
  let component: ConsultazioneIstanzePageComponent;
  let fixture: ComponentFixture<ConsultazioneIstanzePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultazioneIstanzePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultazioneIstanzePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
