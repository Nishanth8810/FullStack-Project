import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';
import { FormBuilder, Validators } from '@angular/forms';
import { UserServiceService } from '../../../Services/user-service.service';
import { NgToastService } from 'ng-angular-popup';
import { Router } from '@angular/router';
import ValidateForm from '../../../helpers/validateForm';
import { NgxSpinnerService } from 'ngx-spinner';


@Component({
  selector: 'app-trainer-settings',
  templateUrl: './trainer-settings.component.html',
  styleUrl: './trainer-settings.component.scss'
})
export class TrainerSettingsComponent implements OnInit {


  imgUrl: any;
  Userimage:any;
  userDataForm:any;
  email:any;
  constructor(
    private auth:AuthService,
    private fb:FormBuilder,
    private userStore:UserServiceService,
    private toast:NgToastService,
    private router:Router,
    private spinner: NgxSpinnerService

  ){}

  ngOnInit(): void {
    this.spinner.show();
   this.loadData();
   this.userDataForm = this.fb.group({
    firstname: ['', Validators.required],
    lastname: ['', Validators.required],
    usermail: ['', Validators.required],
    certification: [],
    specializations: ['', Validators.required],
    yearsOfExperience: ['', Validators.required],
    previousEmployment: ['', Validators.required],
    preferredCommunication: ['', Validators.required],
    description: ['', Validators.required],
    schedule:['',Validators.required]
   })
  }

  loadData(){
    this.auth.getTrainerDetails().subscribe((res)=>{

      this.imgUrl = res.imageUrl;
      this.userDataForm.patchValue({
        firstname:res.firstName,
        lastname:res.lastName,
        usermail:res.email,
        certification:res.certification,
        specializations:res.specializations,
        yearsOfExperience:res.yearsOfExperience,
        previousEmployment:res.previousEmployment,
        description:res.description,
        preferredCommunication:res.preferredCommunication,
        schedule:res.schedule
      })
      this.spinner.hide();
    })
  }
  submitUserData() {

    if (this.userDataForm.valid) {
      const res = this.userDataForm.value;
      const data={
        firstname:res.firstName,
        lastname:res.lastName,
        usermail:res.email,
        certification:res.certification,
        specializations:res.specializations,
        yearsOfExperience:res.yearsOfExperience,
        previousEmployment:res.previousEmployment,
        description:res.description,
        preferredCommunication:res.preferredCommunication,
        schedule:res.schedule
      }
      this.auth.editTrainerData(data,this.userDataForm.value.usermail).subscribe({
        next: (res:any) => {


          if (res === "ACCEPTED") {
            this.toast.info({ detail: "SUCCESS"});
            this.router.navigate(['trainer-setting']);
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
     this.loadData();
     if (res === "ACCEPTED") {
      this.toast.info({ detail: "SUCCESS"});
      this.router.navigate(['physical-stats']);
    }
    else{
      this.toast.error({ detail:"Error" })
  }
    })
  }
}
