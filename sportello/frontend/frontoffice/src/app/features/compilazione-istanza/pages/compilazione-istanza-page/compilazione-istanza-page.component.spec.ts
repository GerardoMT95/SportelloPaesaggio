import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompilazioneIstanzaPageComponent } from './compilazione-istanza-page.component';

describe('CompilazioneIstanzaPageComponent', () => {
  let component: CompilazioneIstanzaPageComponent;
  let fixture: ComponentFixture<CompilazioneIstanzaPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompilazioneIstanzaPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompilazioneIstanzaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
