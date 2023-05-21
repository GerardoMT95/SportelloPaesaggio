import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiepilogoIntegrazioneDocumentaleComponent } from './riepilogo-integrazione-documentale.component';

describe('RiepilogoIntegrazioneDocumentaleComponent', () => {
  let component: RiepilogoIntegrazioneDocumentaleComponent;
  let fixture: ComponentFixture<RiepilogoIntegrazioneDocumentaleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiepilogoIntegrazioneDocumentaleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiepilogoIntegrazioneDocumentaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
