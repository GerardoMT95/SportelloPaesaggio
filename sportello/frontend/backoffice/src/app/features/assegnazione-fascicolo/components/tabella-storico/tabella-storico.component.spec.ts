import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaStoricoComponent } from './tabella-storico.component';

describe('TabellaStoricoComponent', () => {
  let component: TabellaStoricoComponent;
  let fixture: ComponentFixture<TabellaStoricoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabellaStoricoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabellaStoricoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
