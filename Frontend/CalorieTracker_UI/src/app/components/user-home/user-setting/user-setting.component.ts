import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../../../_admin-services/recipe.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-user-setting',
  templateUrl: './user-setting.component.html',
  styleUrl: './user-setting.component.scss'
})
export class UserSettingComponent implements OnInit {
  SavedRecipe: any[] = [];
  imageUrl!: string;
  constructor(
    private recipeService: RecipeService,
    private spinner: NgxSpinnerService,

  ) { }

  ngOnInit(): void {
    this.spinner.show();
    this.loadSaved();
  }

  loadSaved() {
    this.recipeService.loadSavedRecipeOfUser().subscribe((res) => {
      this.SavedRecipe = res;
      this.spinner.hide()
    })
  }

  deleteFromSaved(id: number): void {
      this.recipeService.deleteFromSaved(id).subscribe(
        () => {
          this.loadSaved();
        }
      );
    
  }


}
