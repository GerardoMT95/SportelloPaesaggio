import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PptrComponent } from './pptr.component';

describe('PptrComponent', () => {
  let component: PptrComponent;
  let fixture: ComponentFixture<PptrComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PptrComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PptrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
