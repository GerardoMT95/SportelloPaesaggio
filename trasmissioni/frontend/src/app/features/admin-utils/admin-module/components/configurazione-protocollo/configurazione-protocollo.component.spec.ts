import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigurazioneProtocolloComponent } from './configurazione-protocollo.component';

describe('ConfigurazioneProtocolloComponent', () => {
  let component: ConfigurazioneProtocolloComponent;
  let fixture: ComponentFixture<ConfigurazioneProtocolloComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigurazioneProtocolloComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigurazioneProtocolloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
