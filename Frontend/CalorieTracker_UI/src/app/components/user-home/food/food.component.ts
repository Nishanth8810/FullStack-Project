import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../../../_admin-services/recipe.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.scss'] 
})
export class FoodComponent implements OnInit {
  reasonForReport: string = ''; 
  recipeData: any;
  imageUrl!:string;
  savetext:string='save';
  reportReason: any;
 
  constructor(
    private  recipeService:RecipeService,
    private route: ActivatedRoute,
    private toast:NgToastService,
    private router:Router,
    private spinner: NgxSpinnerService

  ){}
ngOnInit(): void {
  const idString: string | null = this.route.snapshot.paramMap.get('id');
  const numberId: number = idString ? parseInt(idString, 10) : 0; 
  this.spinner.show();
  this.recipeService.getById(numberId).subscribe((res:any)=>{
  this.recipeData=res;
  this.imageUrl=res.imageUrl;
  this.spinner.hide()
})

}
saveRecipe(id: number) {
  this.recipeService.saveRecipeUser(id).subscribe((res: string) => {
    if (res === 'CREATED') {
      this.savetext = 'saved';
      this.toast.success({ detail:"Success" , summary: "Recipe saved successfully", duration: 5000 });
    } else if (res === 'ALREADY_REPORTED') {
      this.savetext = 'saved';
      // this.toast.info({ detail:"Info", summary:  "Recipe is already in your saved list", duration: 5000 });
    } else {
      this.toast.error({ detail: res, summary: "Error", duration: 5000 }); 
    }
  }, error => {
    console.error("Error occurred:", error);
    this.toast.error({ detail: "Something went wrong", summary: "Error", duration: 5000 });
  });
}
  
  confirmToSend(recipeId: any) {
    this.recipeService.reportRecipe(recipeId,this.reasonForReport).subscribe((res)=>{
      this.closeModal()
      this.toast.success({detail:"SUCCESS",summary:"Reported",duration:2000});
      this.reasonForReport='reported';
    })
  }
  closeModal() {
    const modal = document.getElementById('exampleModal');
    if (modal) {
      modal.classList.remove('show');
    
    }
  }


}
