export interface Recipe {
  authorId: number;
  name: string;
  prepTimeMinutes: number;
  cookTimeMinutes: number;
  imageUrl: string;
  ingredients: { [key: string]: string }[];
  directionList:  { [key: string]: string }[];
  calories:string;
  fats:string;
  carbs:string;
  proteins:string;
}

