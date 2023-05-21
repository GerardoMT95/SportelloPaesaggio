import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompilazioneAllegatiPageComponent } from './compilazione-allegati-page.component';

describe('CompilazioneAllegatiPageComponent', () => {
  let component: CompilazioneAllegatiPageComponent;
  let fixture: ComponentFixture<CompilazioneAllegatiPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompilazioneAllegatiPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompilazioneAllegatiPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
