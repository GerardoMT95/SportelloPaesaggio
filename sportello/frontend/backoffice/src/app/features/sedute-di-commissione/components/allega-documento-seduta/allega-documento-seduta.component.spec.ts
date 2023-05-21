import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllegaDocumentoSedutaComponent } from './allega-documento-seduta.component';

describe('AllegaDocumentoSedutaComponent', () => {
  let component: AllegaDocumentoSedutaComponent;
  let fixture: ComponentFixture<AllegaDocumentoSedutaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllegaDocumentoSedutaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllegaDocumentoSedutaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
