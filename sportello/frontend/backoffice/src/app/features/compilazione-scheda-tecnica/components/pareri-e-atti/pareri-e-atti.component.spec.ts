import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PareriEAttiComponent } from './pareri-e-atti.component';

describe('PareriEAttiComponent', () => {
  let component: PareriEAttiComponent;
  let fixture: ComponentFixture<PareriEAttiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PareriEAttiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PareriEAttiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
