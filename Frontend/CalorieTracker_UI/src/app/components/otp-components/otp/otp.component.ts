import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';

import { NgToastService } from 'ng-angular-popup';
import { NgOtpInputComponent } from 'ng-otp-input';
import { AuthService } from '../../../Services/auth.service';
import { UserServiceService } from '../../../Services/user-service.service';

@Component({
  selector: 'app-otp',
  templateUrl: './otp.component.html',
  styleUrl: './otp.component.scss'
})
export class OtpComponent implements OnInit {

  @ViewChild(NgOtpInputComponent, { static: false}) ngOtpInput!:NgOtpInputComponent;
  otpForm!: FormGroup;
  email:any;
  resetPasswordEmail!: string;
  timeLeft: number = 120; 
  timer: any;
  otpInput!:string;

  constructor(private fb: FormBuilder,private auth:AuthService,
    private router:Router,
    private userStore:UserServiceService,
    private toast:NgToastService,
 ) { }

  ngOnInit(): void {
    this.otpForm = this.fb.group({
      otpNumber: ['', [Validators.required]]
    });
    this.email= this.auth.getEmail();

   this.startTimer();
   

  }
  startTimer(): void {
    this.timer = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        clearInterval(this.timer);
        this.timer=121;
      }
    }, 1000);
  }

  submitOTP() {

    this.auth.verifyOtpLogin(this.otpInput,this.email!).subscribe({
      next:
    (res:any)=>{
      if(res.userResponse.role[0].roleName==='TRAINER'){
        this.toast.success({detail:"SUCCESS",summary:"Trainer is verified",duration:5000});
        this.router.navigate(['experience']);
      }
      if(res.userResponse.role[0].roleName==='USER'){
        this.toast.success({detail:"SUCCESS",summary:"User is verified",duration:5000});
        this.router.navigate(['pre-set']);
      }
    },error:(err)=>{  
      if (err.status === 408 ) {
        this.toast.error({detail:"Error",summary:"Otp is expired",duration:5000});
      }else{
        this.toast.error({detail:"Error",summary:"Wrong OTP try again",duration:5000});
      }
    }
       })

  }
  onOtpChange($event: string) {
    this.otpInput=$event;
    }
    
  resendOtp() {
    this.auth.resendOtp(this.email).subscribe(res=>{
      this.timeLeft = 120; 
    clearInterval(this.timer); 
    this.startTimer(); 
    })
    }
    formatTime(): string {
      const minutes = Math.floor(this.timeLeft / 60);
      const seconds = this.timeLeft % 60;
      return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
    }
}
