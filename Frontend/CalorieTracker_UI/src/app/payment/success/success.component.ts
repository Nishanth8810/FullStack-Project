import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../Services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserServiceService } from '../../Services/user-service.service';
import { NgToastService } from 'ng-angular-popup';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrl: './success.component.scss'
})
export class SuccessComponent implements OnInit {
  constructor(private authService:AuthService,
    private route:Router,
    private http:HttpClient,
    private userStore:UserServiceService,
    private toast:NgToastService
    ){}
  value!:any;
  ngOnInit(): void {
    const paymentData ={
      "planName":JSON.parse(sessionStorage.getItem('planName') || '{}'),
      "id":JSON.parse(sessionStorage.getItem('email') || '{}'),
      "amount":JSON.parse(sessionStorage.getItem('amount') || '{}')
    } 
    this.saveTrainerPlan(paymentData).subscribe((res)=>{
      if(res===201){
        this.toast.success({detail:"SUCCESS",summary:"Payment Successfull",duration:5000});
        this.route.navigate(['trainer-dashboard']);
      }
    })
    
  }

  saveTrainerPlan(paymentData:any){
   return this.http.post<any>(`https://api.calocoach.shop/trainer/saveTrainerPlan`,paymentData)
  }
}
