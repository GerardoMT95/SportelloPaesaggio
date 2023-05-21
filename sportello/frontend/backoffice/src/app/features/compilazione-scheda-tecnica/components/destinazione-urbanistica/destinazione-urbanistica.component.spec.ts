import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DestinazioneUrbanisticaComponent } from './destinazione-urbanistica.component';

describe('DestinazioneUrbanisticaComponent', () => {
  let component: DestinazioneUrbanisticaComponent;
  let fixture: ComponentFixture<DestinazioneUrbanisticaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DestinazioneUrbanisticaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DestinazioneUrbanisticaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
