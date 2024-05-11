import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../../../_admin-services/recipe.service';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-recipe-manage',
  templateUrl: './recipe-manage.component.html',
  styleUrl: './recipe-manage.component.scss'
})
export class RecipeManageComponent implements OnInit {


constructor(
  private recipeService:RecipeService,
  private router:Router,
  private spinner: NgxSpinnerService

){}

recipes: any;
ngOnInit(): void {
  this.spinner.show();
this.loadData();
}
viewRecipe(recipeId: number): void {
  
  this.router.navigate(['food', recipeId]);
}

loadData(){
  this.recipeService.getAllRecipe().subscribe((res)=>{
    this.spinner.hide();
    this.recipes=res;
  })
}

unlistRecipe(id: number) {
  this.recipeService.unlistRecipe(id).subscribe((res)=>{
    
    this.loadData();
    
  })
  }
  listRecipe(id: number) {
    this.recipeService.listRecipe(id).subscribe((res)=>{
  
     this.loadData();
    })
    }
}
