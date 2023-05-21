import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneraStampaDomandaComponent } from './genera-stampa-domanda.component';

describe('GeneraStampaDomandaComponent', () => {
  let component: GeneraStampaDomandaComponent;
  let fixture: ComponentFixture<GeneraStampaDomandaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GeneraStampaDomandaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneraStampaDomandaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
