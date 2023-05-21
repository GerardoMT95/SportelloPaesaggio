import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneIstanzaPresentataPageComponent } from './gestione-istanza-presentata-page.component';

describe('GestioneIstanzaPresentataPageComponent', () => {
  let component: GestioneIstanzaPresentataPageComponent;
  let fixture: ComponentFixture<GestioneIstanzaPresentataPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneIstanzaPresentataPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneIstanzaPresentataPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
