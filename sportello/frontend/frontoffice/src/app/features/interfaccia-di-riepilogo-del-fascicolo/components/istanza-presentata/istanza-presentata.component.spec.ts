import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IstanzaPresentataComponent } from './istanza-presentata.component';

describe('IstanzaPresentataComponent', () => {
  let component: IstanzaPresentataComponent;
  let fixture: ComponentFixture<IstanzaPresentataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IstanzaPresentataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IstanzaPresentataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
