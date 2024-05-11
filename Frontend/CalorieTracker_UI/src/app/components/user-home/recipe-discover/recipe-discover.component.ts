import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../../../_admin-services/recipe.service';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { HttpClient } from '@angular/common/http';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-recipe-discover',
  templateUrl: './recipe-discover.component.html',
  styleUrls: ['./recipe-discover.component.scss']
})
export class RecipeDiscoverComponent implements OnInit {
  recipes: any;
  searchQuery: string = '';
  calorieRange: number = 0; 
  autoSearchDelay: number = 500;
  startCalorie: any=0;
  endCalorie: any= 0;

  showCalorieFilter: boolean = false;

  cards: any[] = [];
  pageSize = 8;
  pageIndex = 0;
  totalItems = 0;


  constructor(
    private recipeService: RecipeService,
    private router: Router,
    private http: HttpClient,
    private spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.loadRecipes();
  }

  loadRecipes(): void {
    this.spinner.show();
    this.recipeService.getAllRecipePaginated(this.pageSize, this.pageIndex).subscribe(
      (res: any) => {
        this.recipes = res.content;    
        this.spinner.hide();  
        this.cards = res.content;
        this.totalItems = res.totalElements;

      this.router.navigate([], {
        queryParams: {
          page: this.pageIndex,
          size: this.pageSize
        },
        queryParamsHandling: 'merge', 
        replaceUrl: true 
      },)},
      (error) => {
        console.error('Error fetching recipes:', error);
      }
    );
  }
  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadRecipes();
  }
  search(): void {
    this.spinner.show();
    this.recipeService.searchRecipes(this.searchQuery.trim()).subscribe(
      (results) => {
        this.recipes = results;
        this.spinner.hide();
      },
      (error) => {
        console.error('Error fetching recipes:', error);
        this.spinner.hide();
      }
    );
  }

  autoSearch(): void {
    setTimeout(() => {
      this.search();
    }, this.autoSearchDelay);
  }

  viewRecipe(recipeId: number): void {
    this.router.navigate(['food', recipeId]);
  }

  filterByCalorie(): void {
    let start = 0;
    let end = 1000;
    console.log(this.calorieRange)
  
    if (this.startCalorie) {
      start = parseInt(this.startCalorie, 10);
    }
  
    if (this.endCalorie) {
      end = parseInt(this.endCalorie, 10);
    }
    
    this.http.get<any>(`https://api.calocoach.shop/recipes/getRecipeByCalorieBetween?start=${start}&end=${this.calorieRange}`)
      .subscribe(response => {
        this.recipes = response;
      }, error => {
        console.error('Error fetching recipes by calorie:', error);
      });
  }
  toggleCalorieFilter(): void {
    this.showCalorieFilter = !this.showCalorieFilter;
  }

  // filterByCalorie(): void {
  //   // Add your filter logic here
  //   console.log('Filtered by calorie range:', this.startCalorie, '-', this.endCalorie);
  // }
}
