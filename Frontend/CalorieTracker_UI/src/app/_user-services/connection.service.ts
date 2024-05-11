import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../Services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ConnectionService {
  baseUrl:string="https://api.calocoach.shop/connection/"
  baseUrlmeal:string="https://api.calocoach.shop/meal-plan/"

  // baseUrl:string="http://localhost:9090/connection/"
  // baseUrlmeal:string="http://localhost:9090/meal-plan/"

  constructor(private http:HttpClient,
    private auth:AuthService
  ) { }

  sendRequest(id: any) {
    let userId=localStorage.getItem('userId');
    const data={
      userId:userId,
      trainerId:id
    }
    return this.http.post<any>(`${this.baseUrl}requestConnection`,data)
  }
  getAllPlan() {
    let userId=this.auth.getUserId()
    return this.http.get<any>(`${this.baseUrlmeal}allMealPlan/${userId}`)
  }
  stopManaging(userId: any) {
    let trainerId=this.auth.getUserId()

    return this.http.delete<any>(`${this.baseUrl}stopManaging?trainerId=${trainerId}&userId=${userId}`)
  }
  getStatus(trainerId:any) {
    let userId=this.auth.getUserId()
      return this.http.get(`${this.baseUrl}getStatusOfConnection?trainerId=${trainerId}&userId=${userId}`)
  }
 
  getAllRequest() {
    let userId=this.auth.getUserId()
        return this.http.get(`${this.baseUrl}getAllPendingClients/${userId}`)
  }

  rejectRequest(id: any) {
    let userId=this.auth.getUserId()
        return this.http.get(`${this.baseUrl}acceptRequest?trainerId=${userId}&userId=${id}`)
  }
  acceptRequest(id: any) {
    let userId=this.auth.getUserId()
    return this.http.get(`${this.baseUrl}rejectRequest?trainerId=${userId}&userId=${id}`)
  }
  getAllClients() {
    let userId=this.auth.getUserId()
    return this.http.get(`${this.baseUrl}getAllClients/${userId}`)
  }
 
}
