import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../../../_admin-services/recipe.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgToastService } from 'ng-angular-popup';
import ValidateForm from '../../../helpers/validateForm';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-plan-management',
  templateUrl: './plan-management.component.html',
  styleUrl: './plan-management.component.scss'
})
export class PlanManagementComponent implements OnInit {
  planForm: FormGroup;
  plans:any;

  constructor(
    private planService:RecipeService,
    private fb:FormBuilder,
    private toast:NgToastService,
    private spinner: NgxSpinnerService
  ){
    this.planForm = this.fb.group({
      planName: ['', Validators.required],
      amountControl: ['', Validators.required],
      amountCollaborate: ['', Validators.required],
      numberOfClients: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.spinner.show();
      this.getAllPlans();
  }

  getAllPlans(){
  this.planService.getAllPlan().subscribe((res)=>{
    this.plans=res
    this.spinner.hide();
   })
  }


  onSubmit() {
    if(this.planForm.valid){
      this.planService.savePlan(this.planForm.value).subscribe((res=>{
        this.planForm.reset();
        this.closeModal();
        this.getAllPlans();
        this.toast.success({detail:"SUCCESS",summary:"Plan Saved",duration:1000});
      }))
    }else{
      ValidateForm.ValidateAllFormFields(this.planForm)
    }
      
    }

  closeModal() {
    const modal = document.getElementById('exampleModal');
    if (modal) {
      modal.classList.remove('show');
    }
  }

  listPlan(id: number) {
  this.planService.listPlan(id).subscribe(()=>{
   this.getAllPlans();
  })
  }

  unListPlan(id: number) {
    this.planService.unlistPlan(id).subscribe(()=>{
     this.getAllPlans();
    })
  }
}
