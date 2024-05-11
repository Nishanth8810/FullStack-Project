import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../../../Services/data.service';
import { UserServiceService } from '../../../Services/user-service.service';
import { AuthService } from '../../../Services/auth.service';


@Component({
  selector: 'app-exclusions',
  templateUrl: './exclusions.component.html',
  styleUrl: './exclusions.component.scss'
})
export class ExclusionsComponent implements OnInit {
  commonExclusions: string[] = ['Nothing','Gluten', 'Dairy', 'Nuts', 'Soy', 'Shellfish', 'Eggs', 'Fish'];
  selectedExclusions: string[] = Array(this.commonExclusions.length).fill(false);
  email: any;

  ngOnInit(): void {
    this.email=this.authService.getEmail();
   
  }
  constructor(private route:Router,private dataService:DataService
    ,private authService:AuthService
    ){}
newExclusion: any;
addExclusion() {

}

continue() {
  const selectedExclusions = this.commonExclusions.filter((exclusion, index) => this.selectedExclusions[index]);
  this.dataService.setData('exclusions', selectedExclusions);
  this.route.navigate(['goal']);
}

}
