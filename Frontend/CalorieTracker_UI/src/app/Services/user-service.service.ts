import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { __values } from 'tslib';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  private fullname$ = new BehaviorSubject<string>("");
  private role$ = new BehaviorSubject<String>("");
  private emailSource$ = new BehaviorSubject<string>("");
  private token$ = new BehaviorSubject<string>("");
  private userId$ = new BehaviorSubject<string>("");
  private imageUrl$ = new BehaviorSubject<string>("");


  constructor() { }
  public setEmailFromStore(email: string) {
    this.emailSource$.next(email)
  }

  public setIdFromStore(id: any) {
    this.userId$.next(id);
  }
  public setImageUrlFromStore(imageUrl: any) {
    this.imageUrl$.next(imageUrl);
  }
  public getIdFromStore() {
    return this.userId$.asObservable();
  }
  public getImageFromStore() {
    return this.imageUrl$.asObservable();
  }
  public getEmailFromStore() {
    return this.emailSource$.asObservable();
  }
  public setTokenFromStore(token: string) {
    this.token$.next(token)
  }
  public getTokenFromStore() {
    return this.token$.asObservable();
  }

  public getRoleFromStore() {
    return this.role$.asObservable();
  }
  public setRoleForStore(role: string) {
    this.role$.next(role);
  }
  public getFullNameFromStore() {
    return this.fullname$.asObservable();
  }
  public setFullNameFromStore(fullname: string) {
    this.fullname$.next(fullname);
  }



}
