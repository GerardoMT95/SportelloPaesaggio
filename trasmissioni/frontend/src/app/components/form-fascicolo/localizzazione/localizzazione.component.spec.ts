import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LocalizzazioneComponent } from './localizzazione.component';

describe('LocalizzazioneComponent', () => {
  let component: LocalizzazioneComponent;
  let fixture: ComponentFixture<LocalizzazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocalizzazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocalizzazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
