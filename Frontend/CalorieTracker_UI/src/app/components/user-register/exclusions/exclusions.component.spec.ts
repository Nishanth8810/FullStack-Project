import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExclusionsComponent } from './exclusions.component';

describe('ExclusionsComponent', () => {
  let component: ExclusionsComponent;
  let fixture: ComponentFixture<ExclusionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExclusionsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExclusionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
