import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FascicoloComponent } from './fascicolo.component';

describe('FascicoloComponent', () => {
  let component: FascicoloComponent;
  let fixture: ComponentFixture<FascicoloComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FascicoloComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FascicoloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
