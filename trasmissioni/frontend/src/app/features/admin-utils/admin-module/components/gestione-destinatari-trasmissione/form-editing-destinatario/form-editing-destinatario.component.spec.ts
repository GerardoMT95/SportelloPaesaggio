import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormEditingDestinatarioComponent } from './form-editing-destinatario.component';

describe('FormEditingDestinatarioComponent', () => {
  let component: FormEditingDestinatarioComponent;
  let fixture: ComponentFixture<FormEditingDestinatarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormEditingDestinatarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormEditingDestinatarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
