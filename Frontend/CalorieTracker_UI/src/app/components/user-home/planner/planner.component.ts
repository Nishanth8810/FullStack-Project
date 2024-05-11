import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';
import { UserServiceService } from '../../../Services/user-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MealplanService } from '../../../_user-services/mealplan.service';

@Component({
  selector: 'app-planner',
  templateUrl: './planner.component.html',
  styleUrl: './planner.component.scss'
})
export class PlannerComponent implements OnInit {

  
  constructor(private auth: AuthService,
    private userService: UserServiceService,
    private router:Router,
    private route:ActivatedRoute,
    private mealService:MealplanService

  ) { }

  mealss:any;

  ngOnInit(): void {
    const idString: string | null = this.route.snapshot.paramMap.get('id');
  const numberId: number = idString ? parseInt(idString, 10) : 0; 
  this.mealService.getMealById(numberId).subscribe((res)=>{

    this.mealss=res;
  })
  }


  viewRecipe(recipeId: number): void {
    
    this.router.navigate(['food', recipeId]);
  }
}
