import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VincolisticaComponent } from './vincolistica.component';

describe('VincolisticaComponent', () => {
  let component: VincolisticaComponent;
  let fixture: ComponentFixture<VincolisticaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VincolisticaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VincolisticaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
