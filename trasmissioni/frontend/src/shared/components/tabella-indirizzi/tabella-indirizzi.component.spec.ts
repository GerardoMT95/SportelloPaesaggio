import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaIndirizziComponent } from './tabella-indirizzi.component';

describe('TabellaIndirizziComponent', () => {
  let component: TabellaIndirizziComponent;
  let fixture: ComponentFixture<TabellaIndirizziComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabellaIndirizziComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabellaIndirizziComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
