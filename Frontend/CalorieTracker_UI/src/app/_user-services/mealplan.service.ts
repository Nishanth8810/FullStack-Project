import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserServiceService } from '../Services/user-service.service';

@Injectable({
  providedIn: 'root'
})
export class MealplanService {
  
 
  baseUrl:string="https://api.calocoach.shop/meal-plan/"


  // baseUrl:string="http://localhost:9090/meal-plan/"
  constructor(private http:HttpClient,private auth:UserServiceService) { }
  createPlan(value: FormData) {
    return this.http.post(`${this.baseUrl}create`,value);
  }
  getMealById(numberId: number) {
    return this.http.get(`${this.baseUrl}getPlanById/${numberId}`);
  }
  deletePlan(planId: any) {
    return this.http.delete(`${this.baseUrl}deletePlanById/${planId}`);
  }
}
