import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioTemplateComponent } from './dettaglio-template.component';

describe('DettaglioTemplateComponent', () => {
  let component: DettaglioTemplateComponent;
  let fixture: ComponentFixture<DettaglioTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DettaglioTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DettaglioTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
