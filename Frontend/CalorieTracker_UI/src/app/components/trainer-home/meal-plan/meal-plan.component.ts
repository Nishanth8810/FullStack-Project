import { Component, OnInit } from '@angular/core';
import { ConnectionService } from '../../../_user-services/connection.service';
import { MealplanService } from '../../../_user-services/mealplan.service';
import { NgToastService } from 'ng-angular-popup';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-meal-plan',
  templateUrl: './meal-plan.component.html',
  styleUrl: './meal-plan.component.scss'
})
export class MealPlanComponent implements OnInit{


  constructor(
    private connection:ConnectionService,
    private mealService:MealplanService,
    private toast:NgToastService,
    private spinner: NgxSpinnerService
  ){}

ngOnInit(): void {
  this.spinner.show();
 this.loadData();
}
pageSize = 10;
pageIndex = 0;
totalItems = 0;
sort = 'ASC';

searchQuery: string = '';
mealss:any;
Plans: any;
loadData(){
  this.connection.getAllPlan().subscribe((res)=>{
    this.Plans=res;
    this.spinner.hide();
  
  })
}
getMeal(numberId: any) {
  this.mealService.getMealById(numberId).subscribe((res)=>{

    this.mealss=res;
  })
  }
  stopManaging(userId: any) {
    const confirmDelete = confirm("Are you sure you want to remove ,this is irreversible?");
    if(confirmDelete){
      this.connection.stopManaging(userId).subscribe((res)=>{
        if(res==="OK"){
          this.toast.success({ detail: "SUCCESS", summary: "Deleted", duration: 5000 });
          this.loadData();
        }

      
      })
    }
  }
  filterPlans(): void {
    if (this.searchQuery.trim() !== '') {
      this.Plans = this.Plans.filter((plan:any) =>
        plan.planName.toLowerCase().includes(this.searchQuery.trim().toLowerCase())
      );

    }else if (this.searchQuery.trim() === '') {
    this.loadData();
    } 
    else  {
      this.Plans = this.Plans; 
    }
  }

  sortUsing(option: any): void {
    this.sort = option;
    this.loadData();
  }

  deleteMealPlan(planId: any) {
    return this.mealService.deletePlan(planId).subscribe((res)=>{

      this.loadData();
    })
    }
}