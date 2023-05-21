import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CopyInInputComponent } from './copy-in-input.component';

describe('CopyInInputComponent', () => {
  let component: CopyInInputComponent;
  let fixture: ComponentFixture<CopyInInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CopyInInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CopyInInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
