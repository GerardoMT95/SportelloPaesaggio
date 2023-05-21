import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAttivaModificaComponent } from './admin-attiva-modifica.component';

describe('AdminAttivaModificaComponent', () => {
  let component: AdminAttivaModificaComponent;
  let fixture: ComponentFixture<AdminAttivaModificaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminAttivaModificaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAttivaModificaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
