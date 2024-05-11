import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DataService } from '../../../Services/data.service';
import { AuthService } from '../../../Services/auth.service';
import ValidateForm from '../../../helpers/validateForm';
import { NgToastService } from 'ng-angular-popup';
import { Router } from '@angular/router';
import { UserServiceService } from '../../../Services/user-service.service';
import { UserData } from '../../../models/user.model';

@Component({
  selector: 'app-information',
  templateUrl: './information.component.html',
  styleUrl: './information.component.scss'
})
export class InformationComponent implements OnInit {
  userDataForm!: FormGroup;
  email!: any;
  constructor(private dataService: DataService,
    private fb: FormBuilder,
    private authService: AuthService,
    private toast: NgToastService,
    private router: Router,
    private userStore: UserServiceService

  ) { }
  ngOnInit(): void {
    this.userDataForm = this.fb.group({
      weight: ['', [Validators.required]],
      height: ['', Validators.required],
      age: ['', Validators.required],
      gender: ['', Validators.required],
      activityLevel: ['', Validators.required],
      bodyFat: ['', Validators.required],
      image: ['', Validators.required]
    });
    this.email=this.authService.getEmail();
  }

  submitUserData() {

    if (this.userDataForm.valid) {
      const formData = this.userDataForm.value;
 
      this.authService.saveData(this.userDataForm, this.userDataForm.value.image).subscribe({
        next: (res) => {
    

          if (res.status === "undefined") {
            this.toast.error({ detail: "ERROR", summary: "Something went wrong!" })
          }
          this.toast.success({ detail: "SUCCESS", summary: "Welcome to our app please login!" });
          this.router.navigate(['login']);
        },
        error: (err) => {
          console.log("err",err)

          this.toast.error({ detail: "ERROR", summary: "Something went wrong!" })
        }
      })
    } else {
      ValidateForm.ValidateAllFormFields(this.userDataForm)
    }
  }

  onImageChange(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      const file = (event.target.files[0] as File);
      this.userDataForm.get('image')?.setValue(file);
    }
    const file = event.target.files[0];
    this.userDataForm.patchValue({
      image: file
    });
  }



} 
