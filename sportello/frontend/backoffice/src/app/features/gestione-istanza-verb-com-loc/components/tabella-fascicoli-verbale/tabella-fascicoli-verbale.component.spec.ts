import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaFascicoliVerbaleComponent } from './tabella-fascicoli-verbale.component';

describe('TabellaFascicoliVerbaleComponent', () => {
  let component: TabellaFascicoliVerbaleComponent;
  let fixture: ComponentFixture<TabellaFascicoliVerbaleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabellaFascicoliVerbaleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabellaFascicoliVerbaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
