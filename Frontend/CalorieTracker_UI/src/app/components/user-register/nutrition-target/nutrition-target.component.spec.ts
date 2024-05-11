import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NutritionTargetComponent } from './nutrition-target.component';

describe('NutritionTargetComponent', () => {
  let component: NutritionTargetComponent;
  let fixture: ComponentFixture<NutritionTargetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NutritionTargetComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NutritionTargetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
