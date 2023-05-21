import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovaAssegnazioneComponent } from './nuova-assegnazione.component';

describe('NuovaAssegnazioneComponent', () => {
  let component: NuovaAssegnazioneComponent;
  let fixture: ComponentFixture<NuovaAssegnazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuovaAssegnazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuovaAssegnazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
