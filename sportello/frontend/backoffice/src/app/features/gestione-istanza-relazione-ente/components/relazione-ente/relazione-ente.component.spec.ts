import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelazioneEnteComponent } from './relazione-ente.component';

describe('RelazioneEnteComponent', () => {
  let component: RelazioneEnteComponent;
  let fixture: ComponentFixture<RelazioneEnteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelazioneEnteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelazioneEnteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
