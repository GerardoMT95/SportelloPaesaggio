import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssegnaFascicoloTableComponent } from './assegna-fascicolo-table.component';

describe('AssegnaFascicoloTableComponent', () => {
  let component: AssegnaFascicoloTableComponent;
  let fixture: ComponentFixture<AssegnaFascicoloTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssegnaFascicoloTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssegnaFascicoloTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
