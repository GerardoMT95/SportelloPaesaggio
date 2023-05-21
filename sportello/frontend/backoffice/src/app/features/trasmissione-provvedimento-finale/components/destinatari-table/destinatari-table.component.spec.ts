import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DestinatariTableComponent } from './destinatari-table.component';

describe('DestinatariTableComponent', () => {
  let component: DestinatariTableComponent;
  let fixture: ComponentFixture<DestinatariTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DestinatariTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DestinatariTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
