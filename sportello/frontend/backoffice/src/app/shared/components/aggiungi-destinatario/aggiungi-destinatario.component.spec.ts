import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiungiDestinatarioComponent } from './aggiungi-destinatario.component';

describe('AggiuntiDestinatarioComponent', () => {
  let component: AggiungiDestinatarioComponent;
  let fixture: ComponentFixture<AggiungiDestinatarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AggiungiDestinatarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AggiungiDestinatarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
