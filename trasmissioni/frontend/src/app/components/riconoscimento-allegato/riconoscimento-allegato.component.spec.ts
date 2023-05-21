import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiconoscimentoAllegatoComponent } from './riconoscimento-allegato.component';

describe('RiconoscimentoAllegatoComponent', () => {
  let component: RiconoscimentoAllegatoComponent;
  let fixture: ComponentFixture<RiconoscimentoAllegatoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiconoscimentoAllegatoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiconoscimentoAllegatoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
