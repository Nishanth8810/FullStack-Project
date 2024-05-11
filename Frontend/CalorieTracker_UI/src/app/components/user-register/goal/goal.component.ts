import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../../../Services/data.service';
import { AuthService } from '../../../Services/auth.service';

@Component({
  selector: 'app-goal',
  templateUrl: './goal.component.html',
  styleUrl: './goal.component.scss'
})
export class GoalComponent implements OnInit{
  goalWeight!: number;
  selectedOption!: string;
  email: any;

  ngOnInit(): void {
    this.email=this.authService.getEmail();
  
  }
  constructor(private route:Router,
    private dataService:DataService,
    private authService:AuthService
    ){

  }

  selected:boolean=false;
  selectedGoal: string = ''; 
  exactGoal: string = ''; 

  setGeneralGoal() {
    this.selectedGoal = 'General';
  }

  setExactGoal() {
    this.selectedGoal = 'Exact';
  }

  selectOption(option: string) {
    this.selectedOption = option;
    if (option === 'goalWeight') {
   
     
    }
    // Logic to handle when an option (loseFat, buildMuscle, maintainWeight) is selected
    
  }

  continue() { 
    
    this.route.navigate(['personal-information'])
    if (this.selectedGoal === 'Exact') {
      this.dataService.setData('Goal:', this.exactGoal);
    } 
    if (this.selectedGoal === 'Goal') {
      this.dataService.setData('Goal:', this.selectedGoal);
    } 
 
  }
}
