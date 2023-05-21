import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RubricaEnteComponent } from './rubrica-ente.component';

describe('RubricaEnteComponent', () => {
  let component: RubricaEnteComponent;
  let fixture: ComponentFixture<RubricaEnteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RubricaEnteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RubricaEnteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
