import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterfacciaDiRiepilogoDelFascicoloPageComponent } from './interfaccia-di-riepilogo-del-fascicolo-page.component';

describe('InterfacciaDiRiepilogoDelFascicoloPageComponent', () => {
  let component: InterfacciaDiRiepilogoDelFascicoloPageComponent;
  let fixture: ComponentFixture<InterfacciaDiRiepilogoDelFascicoloPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterfacciaDiRiepilogoDelFascicoloPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterfacciaDiRiepilogoDelFascicoloPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
