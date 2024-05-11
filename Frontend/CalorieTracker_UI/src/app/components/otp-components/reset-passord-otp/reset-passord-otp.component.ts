import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgToastService } from 'ng-angular-popup';
import { Router } from '@angular/router';
import { UserServiceService } from '../../../Services/user-service.service';
import { AuthService } from '../../../Services/auth.service';

@Component({
  selector: 'app-reset-passord-otp',
  templateUrl: './reset-passord-otp.component.html',
  styleUrl: './reset-passord-otp.component.scss'
})
export class ResetPassordOTPComponent implements OnInit {
otpForm!: FormGroup;
email:any='';
constructor(
  private fb:FormBuilder,
  private userStore:UserServiceService,
  private toast:NgToastService,
  private auth:AuthService,
  private router:Router
){

}
  ngOnInit(): void {
    this.otpForm = this.fb.group({
      otpNumber: ['', [Validators.required]]
    });
    this.email=this.auth.getEmail();
  }

submitOTP() {
  if (this.otpForm.valid) {
    const otpValue = this.otpForm.value.otpNumber;
      this.auth.verifyOtp(this.otpForm.value,this.email!).subscribe({next:(res:any)=>{
        this.userStore.setTokenFromStore(res.jwtToken);
          this.toast.success({detail:"SUCCESS",summary:"OTP is verified",duration:5000});
          this.router.navigate(['reset']);
    },
    error:(error)=>{
      this.toast.error({detail:"Error",summary:"Wrong OTP try again",duration:5000});
    }
  })

}

}
}