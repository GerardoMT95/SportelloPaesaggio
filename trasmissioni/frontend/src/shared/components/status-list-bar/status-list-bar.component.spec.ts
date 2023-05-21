import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusListBarComponent } from './status-list-bar.component';

describe('StatusListBarComponent', () => {
  let component: StatusListBarComponent;
  let fixture: ComponentFixture<StatusListBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatusListBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatusListBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
