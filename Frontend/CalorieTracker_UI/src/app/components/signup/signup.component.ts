import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import ValidateForm from '../../helpers/validateForm';
import { AuthService } from '../../Services/auth.service';
import { Router } from '@angular/router';
import { passwordLengthValidator } from '../../helpers/validators';
import { NgToastService } from 'ng-angular-popup';
import { UserServiceService } from '../../Services/user-service.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent implements OnInit {

  type: string = "password";
  isText: boolean = false;
  eyeIcon: string = "fa fa-eye-slash";
  registerForm!: FormGroup;
  loading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private toast: NgToastService,
    private userStore: UserServiceService
  ) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}')]],
      password: ['', [Validators.required, passwordLengthValidator(8)]],
      userType: ['user', Validators.required]
    })
  }

  hideShowPass() {
    this.isText = !this.isText;
    this.isText ? this.eyeIcon = "fa-eye" : this.eyeIcon = "fa-eye-slash";
    this.isText ? this.type = "text" : this.type = "password";


  }
  onSubmit() {

    if (this.registerForm.valid) {
     
      this.auth.setEmail(this.registerForm.value.email)


      this.loading = true;
      if (this.registerForm.value.userType === 'trainer') {
        this.userStore.setEmailFromStore(this.registerForm.value.email);
        this.auth.setEmail(this.registerForm.value.email)

        this.auth.signupTrainer(this.registerForm.value)
          .subscribe({
            next: (res => {
              this.registerForm.reset();
              this.toast.success({ detail: "SUCCESS", summary: "Trainer has registered!!" })
              this.router.navigate(['otp']);
            }),
            error: (err) => {
              this.loading = false;
              this.toast.error({ detail: "ERROR", summary: "Something went wrong" })
            }
          })

      } else {
        this.userStore.setEmailFromStore(this.registerForm.value.email)
        this.auth.signup(this.registerForm.value)
          .subscribe({
            next: (res => {
              this.registerForm.reset();
              this.toast.success({ detail: "SUCCESS", summary: "User has registered!!" })
              this.router.navigate(['otp']);
            }),
            error: (err: HttpErrorResponse) => {
              this.loading = false;
              if (err.status === 409) {
                this.toast.error({ detail: "ERROR", summary: "Email already exists" })
              }
              else {
                this.toast.error({ detail: "ERROR", summary: "Something went wrong" })
              }

            }
          })
      }


    }
    else {

      ValidateForm.ValidateAllFormFields(this.registerForm)
    }
  }
  get password() {
    return this.registerForm.get('password');
  }
}
