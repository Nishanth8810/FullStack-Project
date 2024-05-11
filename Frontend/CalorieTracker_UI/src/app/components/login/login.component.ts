import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import ValidateForm from '../../helpers/validateForm';
import { AuthService } from '../../Services/auth.service';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { UserServiceService } from '../../Services/user-service.service';
import { ResetPasswordService } from '../../Services/reset-password-service/reset-password.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  type: string = "password";
  isText: boolean = false;
  eyeIcon: string = "fa fa-eye-slash";
  loginForm!: FormGroup;
  public resetPasswordEmail!: string;
  public isValidEmail!: boolean;
  textToshow:string='';
  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private toast: NgToastService,
    private userStore: UserServiceService,
    private resetService: ResetPasswordService,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.textToshow="Login";
    // Initialize the login form
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}')]],
      password: ['', Validators.required]
    });
  }

  hideShowPass() {
    // Toggle password visibility
    this.isText = !this.isText;
    this.eyeIcon = this.isText ? "fa-eye" : "fa-eye-slash";
    this.type = this.isText ? "text" : "password";
  }

  onSubmit() {
    // Submit the login form
    this.textToshow="Please Wait..."
    if (this.loginForm.valid) {
      this.auth.login(this.loginForm.value).subscribe({
        next: (res: any) => {
          // Handle successful login
          this.loginForm.reset();
          this.auth.storeToken(res.jwtToken);
          const tokenPayload = this.auth.decodeToken();
          this.userStore.setRoleForStore(tokenPayload.role);
          this.userStore.setEmailFromStore(tokenPayload.sub);
          this.userStore.setIdFromStore(tokenPayload.userId);
          this.auth.storeImage(res.profilePic);
          this.auth.setEmail(tokenPayload.sub);
          this.auth.setUserId(tokenPayload.userId);

          this.toast.success({ detail: "SUCCESS", summary: "Logged In succesfully", duration: 3000 ,position:'topCenter'});
          this.router.navigate(tokenPayload.role === 'ADMIN' ? ['admin'] : tokenPayload.role === 'TRAINER' ? ['trainer-dashboard'] : ['user-dashboard']);
        },
        error: (err) => {
          this.textToshow="Login"
          console.log(err)
          
          // Handle login errors
          if (err.status === 403) {
            this.toast.error({ detail: "ERROR", summary: "User is blocked", duration: 5000 });
          } else if (err.status === 404) {
            this.toast.error({ detail: "ERROR", summary: "Email or password is wrong", duration: 5000, position:'topCenter' });
          } else {
            
            this.toast.error({ detail: "ERROR", summary: "Email or password is wrong", duration: 5000, position:'topCenter' });
          }
        }
      });
    } else {
      ValidateForm.ValidateAllFormFields(this.loginForm);
    }
  }

  checkValidEmail(event: string) {
    // Check if the email is valid
    const value = event;
    const pattern = /^[a-zA-Z0-9_.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,3}$/;
    this.isValidEmail = pattern.test(value);
    return this.isValidEmail;
  }

  confirmToSend() {
    // Confirm to send reset password link
    if (this.checkValidEmail(this.resetPasswordEmail)) {
      this.resetService.sendRestPasswordLink(this.resetPasswordEmail).subscribe({
        next: (res) => {
          if (res === "NOT_FOUND") {
            this.toast.error({ detail: "ERROR", summary: "Enter a valid Email", duration: 3000 });
          } else {
            this.toast.success({ detail: "Success", summary: "Reset Success", duration: 3000 });
            this.userStore.setEmailFromStore(this.resetPasswordEmail);
            this.resetPasswordEmail = '';
            const buttonref = document.getElementById('closeBtn');
            buttonref?.click();
            this.router.navigate(['resetOTP']);
          }
        },
        error: (res) => {
          this.toast.error({ detail: "ERROR", summary: "Enter a valid Email", duration: 3000 });
        }
      });
    }
  }
}
