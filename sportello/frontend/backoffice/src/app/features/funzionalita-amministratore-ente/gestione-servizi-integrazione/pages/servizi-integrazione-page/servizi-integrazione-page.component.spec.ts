import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiziIntegrazionePageComponent } from './servizi-integrazione-page.component';

describe('ServiziIntegrazionePageComponent', () => {
  let component: ServiziIntegrazionePageComponent;
  let fixture: ComponentFixture<ServiziIntegrazionePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiziIntegrazionePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiziIntegrazionePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
