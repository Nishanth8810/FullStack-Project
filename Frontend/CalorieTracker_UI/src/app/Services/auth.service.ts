import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { DataService } from './data.service';
import { UserData } from '../models/user.model';
import { UserServiceService } from './user-service.service';
import { registerModel } from '../models/register.model';


@Injectable({
  providedIn: 'root'
})
export class AuthService {


  private userPayload: any;


  // private baseUrl: string = "https://api.calocoach.shop/user/"
  // private baseurl: string = "https://api.calocoach.shop/"
  // private baseUrlUserDeatil: string ="https://api.calocoach.shop/userDetail/"
  // private baseUrlTrainer:string= "https://api.calocoach.shop/trainer/";
  // private baseUrlMeal:string="https://api.calocoach.shop/meal-plan/";

  private baseUrl: string = "http://localhost:9090/user/"
  private baseurl: string = "http://localhost:9090/"
  private baseUrlUserDeatil: string ="http://localhost:9090/userDetail/"
  private baseUrlTrainer:string= "http://localhost:9090/trainer/";
  private baseUrlMeal:string="http://localhost:9090/meal-plan/";


  userDataForm: any;
  constructor(private http: HttpClient,
    private router: Router,
    private dataService: DataService,
    private userStore: UserServiceService,

  ) {
    this.userPayload = this.decodeToken();
  }
  blockUser(userId: any) {
    return this.http.put<any>(`${this.baseUrl}block/${userId}`, {});
  }

  isUserBlocked(email: any) {

    return this.http.get(`${this.baseUrl}isUserBlocked?email=${email}`);
  }
  getAllTrainersRandom() {
    return this.http.get(`${this.baseUrlTrainer}getAllTrainerFive`);
  }

  unblockUser(userId: any) {
    return this.http.put<any>(`${this.baseUrl}unblock/${userId}`, {});
  }
  getFilter(arg0: string) {
    return this.http.get(`${this.baseUrl}filterByUploadDate?filterOption=${arg0}`)
  }

  signupTrainer(formData: any) {
    const data: registerModel = {
      firstname: formData.firstname,
      lastname: formData.lastname,
      email: formData.email,
      password: formData.password
    };

    return this.http.post<any>(`${this.baseUrl}trainer-register`, data);
  }

  signup(formData: any) {

    const data: registerModel = {
      firstname: formData.firstname,
      lastname: formData.lastname,
      email: formData.email,
      password: formData.password
    };

    return this.http.post<any>(`${this.baseUrl}register`, data);
  }

  login(formData: any) {
    return this.http.post(`${this.baseUrl}login`, formData);
  }

  verifyOtp(otpForm: any, email: string) {
    const data = {
      otpNumber: otpForm.otpNumber,
      email: email
    };
    return this.http.post(`${this.baseUrl}verifyOTP`, data);
  }
  getTrainerById(trainerId: any) {
    return this.http.get(`${this.baseurl}trainer/gettrainer/${trainerId}`)
  }

  getTrainerDetails() {
    let email = localStorage.getItem('email');
    return this.http.get<any>(`${this.baseurl}trainer/trainerSetting/${email}`, {});
  }

  verifyOtpLogin(otpForm: any, email: string) {
    const data = {
      otpNumber: otpForm,
      email: email
    };
    return this.http.post(`${this.baseUrl}verifyOTP`, data);
  }

  getAllTrainers(pageSize: number, pageIndex: number, sortOrder: string) {
    return this.http.get<any>(`${this.baseUrlTrainer}paginated?page=${pageIndex}&size=${pageSize}&sortOrder=${sortOrder}`);
  }
  editData(userDataForm: any, email: string) {
    return this.http.put(`${this.baseUrlUserDeatil}edit/${email}`, userDataForm)
  }
  editTrainerData(userDataForm: any, userEmail: any) {
    console.log(userEmail);

    return this.http.put(`${this.baseurl}trainer/edit/${userEmail}`, userDataForm)
  }


  trainerDetails(value: any, image: File) {
    const formData: FormData = new FormData();
    formData.append('TrainerDataReq', JSON.stringify(value));
    formData.append('imageFile', image);
    return this.http.post(this.baseurl + 'trainer/saveTrainerData', formData);
  }

  saveTrainerPlan(value: any) {
    return this.http.post(`${this.baseurl}trainer/saveTrainerData`, value);
  }

  getUserDetails() {
    let email = localStorage.getItem('email');

    return this.http.get<any>(`${this.baseUrlUserDeatil}userSetting/${email}`, {});
  }


  findAll(size: number, index: number, sort: string) {
    return this.http.get(`${this.baseUrlUserDeatil}paginated?page=${index}&size=${size}&sortOrder=${sort}`);
  }


  resendOtp(email: string) {
    return this.http.get<any>(`${this.baseUrl}resendOTP/${email}`, {});
  }

  saveData(userDataForm: FormGroup, imageFile: File) {
    let email: any = localStorage.getItem('email');
    const userdata: UserData = {
      exclusions: this.dataService.getData('exclusions'),
      goal: 'Build Muscle',
      preDiet: this.dataService.getData('presetDiet'),
      height: userDataForm.value.height,
      bodyFat: userDataForm.value.bodyFat,
      activityLevel: userDataForm.value.activityLevel,
      age: userDataForm.value.age,
      weight: userDataForm.value.weight,
      gender: userDataForm.value.gender,
      userEmail: email
    }
   
    const formData: FormData = new FormData();
    formData.append('userDataReq', JSON.stringify(userdata));
    formData.append('imageFile', imageFile);


    return this.http.post<any>(`${this.baseUrlUserDeatil}saveData`, formData);
  }
  changeImage(image: File, email: string) {
    const formData: FormData = new FormData();
    formData.append('imageFile', image);
    return this.http.put(`${this.baseUrlUserDeatil}editPicture/${email}`, formData)
  }
  getAllPlan() {
    return this.http.get<any>(`${this.baseUrlTrainer}getAllPlan`);
  }
  getUserMeal() {
    let userId = localStorage.getItem('userId');
    return this.http.get<any>(`${this.baseUrlMeal}mealPlan/${userId}`);
  }


  storeToken(tokenValue: string) {
    localStorage.setItem('token', tokenValue);
  }
  storeImage(url: string) {
    localStorage.setItem('image', url);
  }
  getImage() {
    return localStorage.getItem('image');
  }
  getToken(): string | null {
    if (typeof localStorage !== 'undefined') {
      return localStorage.getItem('token');
    }
    return null;
  }

  setEmail(email: string) {
    localStorage.setItem('email', email)
  }
  setUserId(id: any) {
    localStorage.setItem('userId', id)
  }
  getEmail() {
    return localStorage.getItem('email');
  }
  getUserId() {
    let userId = localStorage.getItem('userId');
    return userId;
  }

  isLoggedIn() {
    return !!localStorage.getItem('token');
  }

  isAdmin() {
    let role: any;
    this.userStore.getRoleFromStore().subscribe(res => {
      role = res;
    })
    if (role === 'ADMIN') {
      return true;
    } else {
      return false;
    }
  }

  logOut() {
    localStorage.clear();
    this.router.navigate(['login'])
  }

  decodeToken() {
    const jwtHelper = new JwtHelperService();
    const token = this.getToken()!;
    return jwtHelper.decodeToken(token)
  }

  getFullNameFromToken() {
    if (this.userPayload)
      return this.userPayload.name;
  }

  getRoleFromToken() {
    if (this.userPayload)
      return this.userPayload.role;
  }

}
