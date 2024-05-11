export interface MealPlanRequest {
  id: number;
  trainerId: number;
  clientId: number;
  startDate: string;
  endDate: string;
  meals: Meal[];
}

export interface Meal {
  mealType: string;
  mealItems: MealItem[];
}

export interface MealItem {
  recipeId: number;
}
