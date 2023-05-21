import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CdsEditComponent } from './cds-edit.component';

describe('CdsEditComponent', () => {
  let component: CdsEditComponent;
  let fixture: ComponentFixture<CdsEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CdsEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CdsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
