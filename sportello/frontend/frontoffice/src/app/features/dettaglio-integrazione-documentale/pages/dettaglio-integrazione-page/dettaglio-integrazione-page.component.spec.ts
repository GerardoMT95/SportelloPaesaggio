import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioIntegrazionePageComponent } from './dettaglio-integrazione-page.component';

describe('DettaglioIntegrazionePageComponent', () => {
  let component: DettaglioIntegrazionePageComponent;
  let fixture: ComponentFixture<DettaglioIntegrazionePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DettaglioIntegrazionePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DettaglioIntegrazionePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
