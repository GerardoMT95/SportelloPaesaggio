import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovaComunicazioneComponent } from './nuova-comunicazione.component';

describe('NuovaComunicazioneComponent', () => {
  let component: NuovaComunicazioneComponent;
  let fixture: ComponentFixture<NuovaComunicazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuovaComunicazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuovaComunicazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
