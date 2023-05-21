import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllegaParereComponent } from './allega-parere.component';

describe('AllegaParereComponent', () => {
  let component: AllegaParereComponent;
  let fixture: ComponentFixture<AllegaParereComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllegaParereComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllegaParereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
