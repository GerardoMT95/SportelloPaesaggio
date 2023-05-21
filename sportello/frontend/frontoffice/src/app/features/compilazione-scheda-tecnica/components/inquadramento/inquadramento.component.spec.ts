import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InquadramentoComponent } from './inquadramento.component';

describe('InquadramentoComponent', () => {
  let component: InquadramentoComponent;
  let fixture: ComponentFixture<InquadramentoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InquadramentoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InquadramentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
