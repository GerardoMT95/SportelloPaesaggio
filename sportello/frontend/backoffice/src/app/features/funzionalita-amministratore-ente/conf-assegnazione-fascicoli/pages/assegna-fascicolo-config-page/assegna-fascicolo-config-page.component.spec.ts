import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssegnaFascicoloConfigComponent } from './assegna-fascicolo-config.component';

describe('AssegnaFascicoloComponent', () => {
  let component: AssegnaFascicoloConfigComponent;
  let fixture: ComponentFixture<AssegnaFascicoloConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssegnaFascicoloConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssegnaFascicoloConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
