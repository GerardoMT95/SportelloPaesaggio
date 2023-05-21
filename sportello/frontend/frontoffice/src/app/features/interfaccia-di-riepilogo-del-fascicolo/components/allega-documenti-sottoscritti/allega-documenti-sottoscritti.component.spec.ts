import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllegaDocumentiSottoscrittiComponent } from './allega-documenti-sottoscritti.component';

describe('AllegaDocumentiSottoscrittiComponent', () => {
  let component: AllegaDocumentiSottoscrittiComponent;
  let fixture: ComponentFixture<AllegaDocumentiSottoscrittiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllegaDocumentiSottoscrittiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllegaDocumentiSottoscrittiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
