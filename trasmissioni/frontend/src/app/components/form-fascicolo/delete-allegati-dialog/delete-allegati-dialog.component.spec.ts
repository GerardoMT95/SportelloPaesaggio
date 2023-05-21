import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAllegatiDialogComponent } from './delete-allegati-dialog.component';

describe('DeleteAllegatiDialogComponent', () => {
  let component: DeleteAllegatiDialogComponent;
  let fixture: ComponentFixture<DeleteAllegatiDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteAllegatiDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteAllegatiDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
