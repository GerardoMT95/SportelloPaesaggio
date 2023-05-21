import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompilazioneSchedaTecnicaPageComponent } from './compilazione-scheda-tecnica-page.component';

describe('CompilazioneSchedaTecnicaPageComponent', () => {
  let component: CompilazioneSchedaTecnicaPageComponent;
  let fixture: ComponentFixture<CompilazioneSchedaTecnicaPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompilazioneSchedaTecnicaPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompilazioneSchedaTecnicaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
