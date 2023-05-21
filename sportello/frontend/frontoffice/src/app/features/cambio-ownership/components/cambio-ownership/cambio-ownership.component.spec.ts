import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CambioOwnershipComponent } from './cambio-ownership.component';

describe('CambioOwnershipComponent', () => {
  let component: CambioOwnershipComponent;
  let fixture: ComponentFixture<CambioOwnershipComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CambioOwnershipComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CambioOwnershipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
