import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import ValidateForm from '../../../helpers/validateForm';
import { Router } from '@angular/router';
import { AuthService } from '../../../Services/auth.service';
import { UserServiceService } from '../../../Services/user-service.service';
import { TrainerModel } from '../../../models/trainer-model';

@Component({
  selector: 'app-trainer-experience',
  templateUrl: './trainer-experience.component.html',
  styleUrl: './trainer-experience.component.scss'
})
export class TrainerExperienceComponent implements OnInit {
  trainerDataForm!: FormGroup;
  email!: any;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private userStore: UserServiceService
  ) {
    this.initForm();
  }
  ngOnInit(): void {
    this.email= this.authService.getEmail();
  }

  private initForm(): void {
    this.trainerDataForm = this.formBuilder.group({
      certification: [],
      userEmail: [],
      specializations: ['', Validators.required],
      yearsOfExperience: ['', Validators.required],
      previousEmployment: ['', Validators.required],
      image: ['', Validators.required],
      education: ['', Validators.required],
      schedule: ['', Validators.required],
      preferredCommunication: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  getTrainerDataForm(): FormGroup {
    return this.trainerDataForm;
  }

  submitTrainerData(): void {
    if (this.trainerDataForm.valid) {
      this.trainerDataForm.patchValue({
        userEmail: this.email
      });
      if (this.trainerDataForm.valid) {
        const formData = this.trainerDataForm.value;
        const trainerModel: TrainerModel = {
          certification: formData.certification,
          specializations: formData.specializations,
          yearsOfExperience: formData.yearsOfExperience,
          previousEmployment: formData.previousEmployment,
          education: formData.education,
          schedule: formData.schedule,
          preferredCommunication: formData.preferredCommunication,
          description: formData.description,
          userEmail: formData.userEmail,
        }

        this.authService.trainerDetails(trainerModel, this.trainerDataForm.value.image).subscribe((res) => {
        });

        this.router.navigate(['rates-service']);
      } else {
        ValidateForm.ValidateAllFormFields(this.trainerDataForm);
      }
    }
  }
  onImageChange(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      const file = (event.target.files[0] as File);
      this.trainerDataForm.get('image')?.setValue(file);
    }
    const file = event.target.files[0];
    this.trainerDataForm.patchValue({
      image: file
    });
  }
}
