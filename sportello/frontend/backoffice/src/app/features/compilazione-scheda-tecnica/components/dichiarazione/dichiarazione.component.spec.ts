import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DichiarazioneComponent } from './dichiarazione.component';

describe('DichiarazioneComponent', () => {
  let component: DichiarazioneComponent;
  let fixture: ComponentFixture<DichiarazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DichiarazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DichiarazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
