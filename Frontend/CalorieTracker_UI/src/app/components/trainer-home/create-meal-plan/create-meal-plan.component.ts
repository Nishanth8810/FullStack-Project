import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RecipeService } from '../../../_admin-services/recipe.service';
import { NgToastService } from 'ng-angular-popup';
import { MealplanService } from '../../../_user-services/mealplan.service';
import { Router } from '@angular/router';
import { MealPlanRequest } from '../../../models/mealPlan';
import ValidateForm from '../../../helpers/validateForm';
import { ConnectionService } from '../../../_user-services/connection.service';
import { AuthService } from '../../../Services/auth.service';
import { UserServiceService } from '../../../Services/user-service.service';


@Component({
  selector: 'app-create-meal-plan',
  templateUrl: './create-meal-plan.component.html',
  styleUrl: './create-meal-plan.component.scss'
})
export class CreateMealPlanComponent implements OnInit {


  clientList: any;
  show: boolean = false;
  selectedUser: any;
  MealType: any;
  recipes: any;
  breakFastrecipeData: any;
  imageUrl: any;
  totalCalories: number = 0;
  mealPlanForm: any;
  searchQuery: any;
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private recipeService: RecipeService,
    private toast: NgToastService,
    private mealPlanService: MealplanService,
    private connection: ConnectionService,
    private authService: AuthService
  ) { }
  autoSearchDelay: number = 500;
  ngOnInit(): void {
    this.mealPlanForm = this.formBuilder.group({
      clientId: ['', Validators.required],
      trainerId: [],
      note: ['', Validators.required],
      planName: ['', Validators.required],
      forDate: ['', Validators.required],
      meals: this.formBuilder.array([]),
      mealsDum: this.formBuilder.array([]),
      totalCalories: [],
      trainerEmail: []
    })
    this.loadRecipes();
    this.getAllClients();
  }

  deleteMealItem(name: string, mealType: string): void {
    const mealsArrayControls = this.mealsArrayDum.controls;
    for (let i = 0; i < mealsArrayControls.length; i++) {
      const meal = mealsArrayControls[i] as FormGroup;
      const mealName = meal.get('name')?.value;
      const mealMealType = meal.get('mealType')?.value;
      if (mealName === name && mealMealType === mealType) {
        const calorie = meal.get('calorie')?.value;
        if (calorie) {
          this.totalCalories -= calorie;
        }
        this.mealsArrayDum.removeAt(i);
        break;
      }
    }
  }


  getAllClients() {
    this.connection.getAllClients().subscribe((res) => {
      this.clientList = res;
    })
  }

  get mealsArray(): FormArray {
    return this.mealPlanForm.get('meals') as FormArray;
  }
  get mealsArrayDum(): FormArray {
    return this.mealPlanForm.get('mealsDum') as FormArray;
  }

  addRecipe(mealType: string): void {
    this.MealType = mealType;

  }

  updateTotalCalories(): void {
    this.totalCalories = 0;
    this.mealsArrayDum.controls.forEach((control: AbstractControl) => {
      if (control instanceof FormGroup) {
        const calorie = control.get('calorie')?.value;
        if (calorie) {
          this.totalCalories += calorie;
        }
      }
    });
  }

  addRecipes(reciped: any, name: string, imageUrl: string, calories: number): void {
    const mealType = this.MealType;
    this.toast.info({ detail: "Added", duration: 600 })
    // For mealsArrayDum
    this.mealsArrayDum.push(
      this.formBuilder.group({
        mealType: [mealType],
        mealItems: [{ recipeId: reciped }],
        imgUrl: [imageUrl],
        name: [name],
        calorie: [calories]
      })
    );
    this.totalCalories += calories;
    // For mealsArray
    let existingMealIndex = -1;
    this.mealsArray.controls.forEach((control: AbstractControl) => {
      if (control instanceof FormGroup && control.get('mealType')?.value === mealType) {
        existingMealIndex = this.mealsArray.controls.indexOf(control);
      }
    });

    if (existingMealIndex !== -1) {
      // If mealType exists, add recipeId to its mealItems array
      const mealItemsArray = (this.mealsArray.controls[existingMealIndex] as FormGroup).get('mealItems') as FormArray;
      mealItemsArray.push(this.formBuilder.group({ recipeId: reciped }));
    } else {
      // If mealType doesn't exist, create a new meal and add it to mealsArray
      this.mealsArray.push(
        this.formBuilder.group({
          mealType: [mealType],
          mealItems: this.formBuilder.array([{ recipeId: reciped }])
        })
      );
    }
  }

  search() {
    if (this.searchQuery.trim() === '') {
      this.loadRecipes();
    }
    else if (this.searchQuery.trim() !== '') {
      this.recipeService.searchRecipes(this.searchQuery)
        .subscribe(results => this.recipes = results);
    }
  }
  autoSearch(): void {
    setTimeout(() => {
      this.search();
    }, this.autoSearchDelay);
  }


  confirmMealPlan(): void {

    if (this.mealPlanForm.valid) {
      if (this.mealsArray.length < 4) {
        this.toast.warning({ detail: "Please add at least 4 meals to submit the form.", duration: 5000 });
        return;
      }
      this.mealPlanForm.patchValue({
        totalCalories: this.totalCalories,
        trainerId:this.authService.getUserId()
      });

      this.mealPlanService.createPlan(this.mealPlanForm.value).subscribe((res) => {
        this.toast.success({ detail: "SUCCESS", summary: "plan saved and Email Sent ", duration: 5000 });
        this.router.navigate(['meal-plan'])
      })


    } else {
      ValidateForm.ValidateAllFormFields(this.mealPlanForm)
    }
  }
  loadRecipes() {
    this.recipeService.getAllRecipe().subscribe(
      (res: any[]) => {


        this.recipes = res.filter(recipe => recipe.unList !== 1);
      },
      (error) => {
        console.error('Error fetching recipes:', error);
      }
    );
  }

  addMeal() {
    throw new Error('Method not implemented.');
  }


}
