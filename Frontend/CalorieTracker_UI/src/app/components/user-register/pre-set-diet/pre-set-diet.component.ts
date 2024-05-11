import { Component, OnInit, ViewChild } from '@angular/core';
import { MatStepper } from '@angular/material/stepper';
import { Router } from '@angular/router';
import { DataService } from '../../../Services/data.service';
import { UserServiceService } from '../../../Services/user-service.service';
import { AuthService } from '../../../Services/auth.service';

@Component({
  selector: 'app-pre-set-diet',
  templateUrl: './pre-set-diet.component.html',
  styleUrl: './pre-set-diet.component.scss'
})
export class PreSetDietComponent implements OnInit {
  email!:any;

  selectedDietType: string = '';
  dietDescriptions: any = {
    'Anything':'Excludes nothing',
    'Keto': 'Low-carb, high-fat diet.',
    'Vegan': 'Plant-based diet excluding animal products.',
    'Paleo': 'Diet based on foods presumed to be available to Paleolithic humans.',
    'Mediterranean': 'Diet inspired by the traditional eating habits of people from Mediterranean countries.',
    
  };
  constructor(private route:Router,
    private dataService:DataService,
    private userStore:UserServiceService,
    private auth:AuthService
    ){}
  ngOnInit(): void {
    this.email= this.auth.getEmail();
 
  }

  continue() {
    this.dataService.setData("presetDiet",this.selectedDietType);
    this.route.navigate(['exclusion']);
  }


}
