import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LegittimitaUrbanisticaComponent } from './legittimita-urbanistica.component';

describe('LegittimitaUrbanisticaComponent', () => {
  let component: LegittimitaUrbanisticaComponent;
  let fixture: ComponentFixture<LegittimitaUrbanisticaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LegittimitaUrbanisticaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LegittimitaUrbanisticaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
