import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { RecipeService } from '../../../_admin-services/recipe.service';
import { NgToastService } from 'ng-angular-popup';
import { ActivatedRoute, Router } from '@angular/router';
import ValidateForm from '../../../helpers/validateForm';
import { Recipe } from '../../../models/recipe.model';

@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrl: './recipe-edit.component.scss'
})
export class RecipeEditComponent implements OnInit {
  @ViewChild('directionInput') directionInput: ElementRef | any;
  @ViewChild('ingredientInput') ingredientInput : ElementRef | any;
  
  recipeForm!: FormGroup;
  imageUrl: any;
constructor(
  private formBuilder: FormBuilder,
  private router:Router,
  private recipeService:RecipeService,
  private toast:NgToastService,
  private route: ActivatedRoute,
  
  ) { }

directionfield:boolean=false;
ingredientFiled:boolean=false;
recipeId!: number;

ngOnInit(): void {
  this.recipeForm = this.formBuilder.group({
    name: ['', Validators.required],
    prepTimeMinutes: ['', Validators.required],
    cookTimeMinutes: ['', Validators.required],
    image: ['', Validators.required],
    calories: ['', Validators.required],
    proteins: ['', Validators.required],
    carbs: ['', Validators.required],
    fats: ['', Validators.required]
  });
  this.route.params.subscribe(params => {
    this.recipeId = +params['id']; 
    this.loadRecipeDetails();
  });

}

loadRecipeDetails() {
  this.recipeService.getById(this.recipeId).subscribe(recipe => {
    this.recipeForm.patchValue({
      name: recipe.name,
      prepTimeMinutes: recipe.prepTimeMinutes,
      cookTimeMinutes: recipe.cookTimeMinutes,
      image: recipe.imageUrl,
      calories: recipe.nutrients.calories,
      proteins: recipe.nutrients.proteins,
      carbs: recipe.nutrients.carbs,
      fats: recipe.nutrients.fats,
    });
    this.imageUrl=recipe.imageUrl;
    this.directions = recipe.directionList.map((direction: { directions: any; }) => direction.directions);
    this.ingredients = recipe.ingredients.map((ingredient: { name: any; }) => ingredient.name);

    this.recipeForm.controls['directions'].setValue(this.directions);
    this.recipeForm.controls['ingredients'].setValue(this.ingredients);
  });
}


ingredients:string[]=[];
directions:string[]=[];

submitRecipe(): void {
  if(this.ingredients.length===0){    
  this.ingredientFiled=true;
    
  }
  if(this.directions.length===0){
    this.directionfield=true;
    
  }

  const mappedIngredients: { [key: string]: string }[] = [];
  this.ingredients.forEach(ingredient => {
  const ingredientObj:any = {};
  ingredientObj['name'] = ingredient; 
  mappedIngredients.push(ingredientObj);
});
const mappedDirection: { [key: string]: string }[] = [];
this.directions.forEach(direction => {
  const directionObj:any = {};
  directionObj['directions'] = direction; 
  mappedDirection.push(directionObj);
});
  if (this.recipeForm.valid) {
    const formData = this.recipeForm.value;
    const recipe: Recipe = {
      authorId: 34,
      name: formData.name,
      prepTimeMinutes: formData.prepTimeMinutes,
      cookTimeMinutes: formData.cookTimeMinutes,
      imageUrl: formData.image.name,
      ingredients: mappedIngredients,
      directionList: mappedDirection,
      calories: formData.calories,
      fats: formData.fats,
      carbs: formData.carbs,
      proteins: formData.proteins,
  
    };
 
    this.recipeService.editRecipe(recipe,this.recipeForm.value.image).subscribe((res)=>{
      
      if(res==='CREATED'){
        this.router.navigate(['admin']);
        this.toast.success({detail:"Success",summary:"Recipe added Successfully",duration:5000});
        this.recipeForm.reset();
      }else{
        this.toast.error({detail:"ERROR",summary:"Something went wrong",duration:5000})
      }
    })
  } else {

    ValidateForm.ValidateAllFormFields(this.recipeForm)
  }
}


addIngredient(ingredient: string) {
  this.ingredients.push(ingredient);
  this.ingredientFiled=false;
  this.ingredientInput.nativeElement.value = '';

  
}

addDirection(direction: string) {
  this.directions.push(direction);
  this.directionfield=false;
  this.directionInput.nativeElement.value = '';

}


onImageChange(event:any) {
  if (event.target.files && event.target.files.length > 0) {
    const file = (event.target.files[0] as File);
    this.recipeForm.get('image')?.setValue(file);
  }
  const file = event.target.files[0];
  this.recipeForm.patchValue({
    image: file
  });
}

cancel() {
 this.router.navigate(['admin'])
}

}