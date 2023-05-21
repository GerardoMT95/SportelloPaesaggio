import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericModalFormComponent } from './generic-modal-form.component';

describe('GenericModalFormComponent', () => {
  let component: GenericModalFormComponent;
  let fixture: ComponentFixture<GenericModalFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenericModalFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericModalFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
