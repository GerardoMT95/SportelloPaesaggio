import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioComComponent } from './dettaglio-com.component';

describe('DettaglioComComponent', () => {
  let component: DettaglioComComponent;
  let fixture: ComponentFixture<DettaglioComComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DettaglioComComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DettaglioComComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
