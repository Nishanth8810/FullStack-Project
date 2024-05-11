import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';
import { FormBuilder, Validators } from '@angular/forms';
import { NgToastService } from 'ng-angular-popup';
import ValidateForm from '../../../helpers/validateForm';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';


@Component({
  selector: 'app-physical-stats',
  templateUrl: './physical-stats.component.html',
  styleUrl: './physical-stats.component.scss'
})
export class PhysicalStatsComponent implements OnInit {
  imgUrl: any;
  Userimage:any;
  userDataForm:any;
  bmi:any
  email:any;
  constructor(
    private auth:AuthService,
    private fb:FormBuilder,
    private toast:NgToastService,
    private router:Router,
    private spinner: NgxSpinnerService
  ){}
  ngOnInit(): void {
    this.spinner.show();
   this.loadPhysicalData();
   this.userDataForm = this.fb.group({
    weight: ['', [Validators.required]],
    height: ['', Validators.required],
    age: ['', Validators.required],
    gender: ['', Validators.required],
    activityLevel: ['', Validators.required],
    bodyFat: ['', Validators.required],
    firstname: ['', Validators.required],
    lastname: ['', Validators.required],
    userEmail: ['', Validators.required],
  });
  this.Userimage=this.fb.group({
    image: ['', Validators.required]
  })
  this.email=this.auth.getEmail();
 
  }

  loadPhysicalData(){
    this.auth.getUserDetails().subscribe((res)=>{
      this.imgUrl =res.profilePic;
      this.userDataForm.patchValue({
      weight:res.weight,
      height:res.height,
      age:res.age,
      gender:res.gender,
      firstname:res.firstname,
      lastname:res.lastname,
      bodyFat:res.bodyFat,
      activityLevel:res.activityLevel,
      userEmail:res.userEmail
      })
      this.spinner.hide();  
      this.bmi = this.calculateBMI(res.weight, res.height);
    })
    
  }
  calculateBMI(weight: number, height: number): number {
    
    const heightInMeters = height / 100; //
    const bmi = weight / (heightInMeters * heightInMeters);
    return parseFloat(bmi.toFixed(2)); 
  }
  onImageChange(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      const file = (event.target.files[0] as File);
      this.Userimage.get('image')?.setValue(file);
    }
    const file = event.target.files[0];
    this.Userimage.patchValue({
      image: file
    });
    this.auth.changeImage(this.Userimage.value.image,this.userDataForm.value.userEmail).subscribe((res)=>{
     this.loadPhysicalData();
     if (res === "ACCEPTED") {
      this.toast.info({ detail: "SUCCESS"});
      this.router.navigate(['physical-stats']);
    }
    else{
      this.toast.error({ detail:"Error" })
  }
    })
  }
  submitUserData() {    
    if (this.userDataForm.valid) {
      const formData = this.userDataForm.value;
      const data={
      weight:formData.weight,
      height:formData.height,
      age:formData.age,
      gender:formData.gender,
      firstname:formData.firstname,
      lastname:formData.lastname,
      bodyFat:formData.bodyFat,
      activityLevel:formData.activityLevel,
      userEmail:formData.userEmail

      }
      this.auth.editData(data,this.userDataForm.value.userEmail).subscribe({
        next: (res:any) => {


          if (res === "ACCEPTED") {
            this.toast.info({ detail: "SUCCESS"});
            this.router.navigate(['physical-stats']);
          }
        else{
       this.toast.error({ detail:"Error" })
        }
        },
        error: (err) => {


          this.toast.error({ detail: "ERROR", summary: "Something went wrong!" })
        }
      })
    } else {
      
      ValidateForm.ValidateAllFormFields(this.userDataForm)
    }
  }
}
