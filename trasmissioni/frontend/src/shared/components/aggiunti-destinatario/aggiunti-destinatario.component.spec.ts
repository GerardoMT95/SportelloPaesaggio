import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiuntiDestinatarioComponent } from './aggiunti-destinatario.component';

describe('AggiuntiDestinatarioComponent', () => {
  let component: AggiuntiDestinatarioComponent;
  let fixture: ComponentFixture<AggiuntiDestinatarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AggiuntiDestinatarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AggiuntiDestinatarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
