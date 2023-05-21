import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormCreazioneOrganizzazioneComponent } from './form-creazione-organizzazione.component';

describe('FormCreazioneOrganizzazioneComponent', () => {
  let component: FormCreazioneOrganizzazioneComponent;
  let fixture: ComponentFixture<FormCreazioneOrganizzazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormCreazioneOrganizzazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormCreazioneOrganizzazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
