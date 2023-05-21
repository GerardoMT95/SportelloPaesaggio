import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneraStampaDomandaPageComponent } from './genera-stampa-domanda-page.component';

describe('GeneraStampaDomandaPageComponent', () => {
  let component: GeneraStampaDomandaPageComponent;
  let fixture: ComponentFixture<GeneraStampaDomandaPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GeneraStampaDomandaPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneraStampaDomandaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
