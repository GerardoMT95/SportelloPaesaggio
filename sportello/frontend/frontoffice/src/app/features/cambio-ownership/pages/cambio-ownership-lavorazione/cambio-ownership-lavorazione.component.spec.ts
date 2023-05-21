import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CambioOwnershipLavorazioneComponent } from './cambio-ownership-lavorazione.component';

describe('CambioOwnershipLavorazioneComponent', () => {
  let component: CambioOwnershipLavorazioneComponent;
  let fixture: ComponentFixture<CambioOwnershipLavorazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CambioOwnershipLavorazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CambioOwnershipLavorazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
