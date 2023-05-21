import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DelegatoComponent } from './delegato.component';

describe('DelegatoComponent', () => {
  let component: DelegatoComponent;
  let fixture: ComponentFixture<DelegatoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DelegatoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DelegatoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
