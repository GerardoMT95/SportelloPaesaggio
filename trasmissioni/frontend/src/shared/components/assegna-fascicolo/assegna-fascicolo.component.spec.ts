import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssegnaFascicoloComponent } from './assegna-fascicolo.component';

describe('AssegnaFascicoloComponent', () => {
  let component: AssegnaFascicoloComponent;
  let fixture: ComponentFixture<AssegnaFascicoloComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssegnaFascicoloComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssegnaFascicoloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
