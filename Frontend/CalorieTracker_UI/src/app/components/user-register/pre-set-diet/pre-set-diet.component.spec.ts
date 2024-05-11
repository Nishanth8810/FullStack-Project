import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreSetDietComponent } from './pre-set-diet.component';

describe('PreSetDietComponent', () => {
  let component: PreSetDietComponent;
  let fixture: ComponentFixture<PreSetDietComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreSetDietComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreSetDietComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
