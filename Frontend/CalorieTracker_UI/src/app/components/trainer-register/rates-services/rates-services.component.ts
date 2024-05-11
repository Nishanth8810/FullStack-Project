import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { AuthService } from '../../../Services/auth.service';

@Component({
  selector: 'app-rates-services',
  templateUrl: './rates-services.component.html',
  styleUrls: ['./rates-services.component.scss']
})
export class RatesServicesComponent implements OnInit {
  planName: any="Basic-plan";

  constructor(
    private router: Router,
    private toast: NgToastService,
    private auth: AuthService
  ) { }
  Plan: any[] = []; 
  selectedNumberOfClients:any=5;
  numberOfClients: number= 5;
  amountControl: any;
  amountCollaborate: any;


  ngOnInit(): void {
    this.auth.getAllPlan().subscribe(
      (res) => {
        this.Plan = res;
        this.amountCollaborate=res[0].amountCollaborate;
        this.amountControl=res[0].amountControl;
      },
      (error) => {
        console.error('Error fetching plan data:', error);
      }
    );
  }

  logSelectedNumberOfClients() {  
    const index = this.Plan.findIndex((item, currentIndex) => {
      const selectedNumber = parseInt(this.selectedNumberOfClients, 10); 
      return this.Plan[currentIndex].numberOfClients === selectedNumber;
    });

  
    if (index !== -1) {
      this.amountControl = this.Plan[index].amountControl;
     this.amountCollaborate=this.Plan[index].amountCollaborate;
     this.planName=this.Plan[index].planName;
     
    } else {
  
    }
  }
  paymentControl() {
    this.router.navigate(['checkout'], { 
      queryParams: { 
        amount: this.amountControl,
        planName: this.planName 
      } 
    });
      }
  paymentCollaborate(){
    this.router.navigate(['checkout'], { 
      queryParams: { 
        amount: this.amountCollaborate,
        planName: this.planName 
      } 
    });
  }
}
