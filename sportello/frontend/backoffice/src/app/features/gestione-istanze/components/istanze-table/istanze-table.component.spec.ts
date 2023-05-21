import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IstanzeTableComponent } from './istanze-table.component';

describe('IstanzeTableComponent', () => {
  let component: IstanzeTableComponent;
  let fixture: ComponentFixture<IstanzeTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IstanzeTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IstanzeTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
