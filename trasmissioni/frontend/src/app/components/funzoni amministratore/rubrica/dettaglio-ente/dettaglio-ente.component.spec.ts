import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioEnteComponent } from './dettaglio-ente.component';

describe('DettaglioEnteComponent', () => {
  let component: DettaglioEnteComponent;
  let fixture: ComponentFixture<DettaglioEnteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DettaglioEnteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DettaglioEnteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
