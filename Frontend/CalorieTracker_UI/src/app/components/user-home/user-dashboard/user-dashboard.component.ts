import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';
import { UserServiceService } from '../../../Services/user-service.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrl: './user-dashboard.component.scss'
})
export class UserDashboardComponent implements OnInit {
  constructor(private auth: AuthService,
    private userService: UserServiceService

  ) { }
trainers:any;
  ngOnInit(): void {
   this.auth.getAllTrainersRandom().subscribe((res)=>{
    this.trainers=res
   })
  }


}
