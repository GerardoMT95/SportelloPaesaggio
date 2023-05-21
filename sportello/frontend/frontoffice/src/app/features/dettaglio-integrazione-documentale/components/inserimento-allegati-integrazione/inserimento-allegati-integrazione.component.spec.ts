import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InserimentoAllegatiIntegrazioneComponent } from './inserimento-allegati-integrazione.component';

describe('InserimentoAllegatiIntegrazioneComponent', () => {
  let component: InserimentoAllegatiIntegrazioneComponent;
  let fixture: ComponentFixture<InserimentoAllegatiIntegrazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InserimentoAllegatiIntegrazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InserimentoAllegatiIntegrazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
