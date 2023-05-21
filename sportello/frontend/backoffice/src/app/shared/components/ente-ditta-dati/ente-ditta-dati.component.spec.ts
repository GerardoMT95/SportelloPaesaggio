import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnteDittaDatiComponent } from './ente-ditta-dati.component';

describe('EnteDittaDatiComponent', () => {
  let component: EnteDittaDatiComponent;
  let fixture: ComponentFixture<EnteDittaDatiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnteDittaDatiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnteDittaDatiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
