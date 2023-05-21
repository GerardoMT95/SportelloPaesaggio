import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovoDestinatarioComponent } from './nuovo-destinatario.component';

describe('NuovoDestinatarioComponent', () => {
  let component: NuovoDestinatarioComponent;
  let fixture: ComponentFixture<NuovoDestinatarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuovoDestinatarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuovoDestinatarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
