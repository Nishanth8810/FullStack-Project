import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../../../Services/user-service.service';
import { AuthService } from '../../../Services/auth.service';

@Component({
  selector: 'app-navbar-user',
  templateUrl: './navbar-user.component.html',
  styleUrl: './navbar-user.component.scss'
})
export class NavbarUserComponent implements OnInit {
  imgUrl: any;
  isHovering: boolean=false;
  constructor(
    private auth:AuthService

  ) { }


  ngOnInit(): void {
    this.imgUrl= this.auth.getImage(); 
    
  }

  logout() {
    this.auth.logOut();
  }
  toggleHover() {
    this.isHovering = !this.isHovering;
  }

}
