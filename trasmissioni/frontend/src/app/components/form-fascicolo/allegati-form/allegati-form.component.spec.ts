import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllegatiFormComponent } from './allegati-form.component';

describe('AllegatiFormComponent', () => {
  let component: AllegatiFormComponent;
  let fixture: ComponentFixture<AllegatiFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllegatiFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllegatiFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
