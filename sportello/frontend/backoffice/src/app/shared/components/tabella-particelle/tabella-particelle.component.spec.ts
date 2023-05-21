import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaParticelleComponent } from './tabella-particelle.component';

describe('TabellaParticelleComponent', () => {
  let component: TabellaParticelleComponent;
  let fixture: ComponentFixture<TabellaParticelleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabellaParticelleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabellaParticelleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
