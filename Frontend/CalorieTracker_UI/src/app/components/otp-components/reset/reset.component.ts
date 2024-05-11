import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ResetPassword } from '../../../models/reset-password.model';
import { ResetPasswordService } from '../../../Services/reset-password-service/reset-password.service';
import { UserServiceService } from '../../../Services/user-service.service';
import { ConfirmPasswordValidator } from '../../../helpers/confirmPasswordValidator';
import { AuthService } from '../../../Services/auth.service';

@Component({
  selector: 'app-reset',
  templateUrl: './reset.component.html',
  styleUrl: './reset.component.scss'
})
export class ResetComponent implements OnInit {
  type:string="password";
  isText:boolean=false;
  eyeIcon:string="fa fa-eye-slash";

  resetPasswordForm!:FormGroup;
  emailToReset!:string;
  emailToken!:string;
  resetPasswordObj=new ResetPassword();

  constructor(private fb:FormBuilder,
    private resetService:ResetPasswordService,
   private userStore:UserServiceService,
   private router:Router,
   private auth:AuthService
    ){

  }
  ngOnInit(): void {
    this.resetPasswordForm=this.fb.group({
      password:[null,Validators.required],
      confirmPassword:[null,Validators.required]
    },{
      validators: ConfirmPasswordValidator('password', 'confirmPassword')
      
    })
  }
  hideShowPass() {
    this.isText=!this.isText;
    this.isText ? this.eyeIcon="fa-eye" :this.eyeIcon= "fa-eye-slash";
    this.isText? this.type="text":this.type="password";
  }
  onSubmit(){
    
    if(this.resetPasswordForm.valid){
      this.resetPasswordObj.email=this.auth.getEmail();
   
      this.resetPasswordObj.newPassword=this.resetPasswordForm.value.password;
      this.userStore.getTokenFromStore().subscribe(res=>{
        this.resetPasswordObj.emailToken = res
      });
      this.resetService.resetPassword(this.resetPasswordObj).
      subscribe({
        next:(res)=>{
         this.router.navigate(['login']);
          
        },
        error:(err)=>{
          console.log(err)
        }
      })
    }
  }
  



}
