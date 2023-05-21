import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaSeduteCommissioniComponent } from './tabella-sedute-commissioni.component';

describe('TabellaSeduteCommissoniComponent', () => {
  let component: TabellaSeduteCommissioniComponent;
  let fixture: ComponentFixture<TabellaSeduteCommissioniComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabellaSeduteCommissioniComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabellaSeduteCommissioniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
