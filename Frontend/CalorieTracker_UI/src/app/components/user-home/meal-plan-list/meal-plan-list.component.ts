import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';
import { UserServiceService } from '../../../Services/user-service.service';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-meal-plan-list',
  templateUrl: './meal-plan-list.component.html',
  styleUrl: './meal-plan-list.component.scss'
})
export class MealPlanListComponent implements OnInit{
  constructor(private auth: AuthService,
    private userService: UserServiceService,
    private router:Router,
    private spinner: NgxSpinnerService

  ) { }

  mealss:any;

  ngOnInit(): void {
    this.spinner.show();
    this.auth.getUserMeal().subscribe((res:any) => {
      this.mealss=res;
      this.spinner.hide();
    })

  }
}
