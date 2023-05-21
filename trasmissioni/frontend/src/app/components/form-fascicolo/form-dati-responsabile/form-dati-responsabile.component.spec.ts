import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormDatiResponsabileComponent } from './form-dati-responsabile.component';

describe('FormDatiResponsabileComponent', () => {
  let component: FormDatiResponsabileComponent;
  let fixture: ComponentFixture<FormDatiResponsabileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormDatiResponsabileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormDatiResponsabileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
