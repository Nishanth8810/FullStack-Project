import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';
import { ConnectionService } from '../../../_user-services/connection.service';
import { MealPlanRequest } from '../../../models/mealPlan';
import { RecipeService } from '../../../_admin-services/recipe.service';
import moment from 'moment';
import { NgToastService } from 'ng-angular-popup';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-manage-clients',
  templateUrl: './manage-clients.component.html',
  styleUrl: './manage-clients.component.scss'
})
export class ManageClientsComponent implements OnInit {

 
  constructor(
    private auth:AuthService,
    private connectionService:ConnectionService,
    private recipeService:RecipeService,
    private toast:NgToastService,
    private spinner: NgxSpinnerService

  ){}
  isDropdownOpen = false;
  users:any[] = [];
  clients: any[] = [];


  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
  ngOnInit(): void {
    this.spinner.show();
   this.loaddata();
  //  this.getAllRecipes();
  this.getAllClients();
  }

  loaddata(){
    this.connectionService.getAllRequest().subscribe((res:any)=>{
      this.users=res;

    })
  }
    reject(id: any) {
      this.connectionService.acceptRequest(id).subscribe((res)=>{
        this.loaddata();
        
      })
    }
    accept(id: any) {
      this.connectionService.rejectRequest(id).subscribe((res)=>{
        this.ngOnInit();
        
      })
    }
    getAllClients(){
      this.connectionService.getAllClients().subscribe((res:any)=>{

        this.clients=res;
        this.spinner.hide();
      })
    }
      stopManaging(userId: any) {
        const confirmDelete = confirm("Are you sure you want to remove ,this is irreversible?");
        if(confirmDelete){
          this.connectionService.stopManaging(userId).subscribe((res)=>{
            if(res==="OK"){
              this.toast.success({ detail: "SUCCESS", summary: "Deleted", duration: 5000 });
              this.loaddata();
              this.getAllClients();
            }
            
          })
        }
      }
      viewStats(arg0: any) {
  
      }
      createPlan(arg0: any) {
      
      }
      viewPlan(arg0: any) {
     
      }

   
    getAllRecipes(){
      this.recipeService.getAllRecipe().subscribe((res)=>{
      })
    }

    getElapsedTime(client:any): string {
      const createdAt = moment(client.clientSince);
      const now = moment();
      const daysDiff = now.diff(createdAt, 'days');
      if (daysDiff > 0) {
        return `${daysDiff} days ago`;
      } else {
        const hoursDiff = now.diff(createdAt, 'hours');
        if (hoursDiff > 0) {
          return `${hoursDiff} hours ago`;
        } else {
          const minutesDiff = now.diff(createdAt, 'minutes');
          return `${minutesDiff} minutes ago`;
        }
      }
    }
}
