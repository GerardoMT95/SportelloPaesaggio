import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovoUlterioriDestinatarioComponent } from './nuovo-ulteriori-destinatario.component';

describe('NuovoUlterioriDestinatarioComponent', () => {
  let component: NuovoUlterioriDestinatarioComponent;
  let fixture: ComponentFixture<NuovoUlterioriDestinatarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuovoUlterioriDestinatarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuovoUlterioriDestinatarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
