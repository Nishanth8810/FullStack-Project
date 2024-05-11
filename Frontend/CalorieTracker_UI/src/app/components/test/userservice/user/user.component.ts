import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Status, User } from '../../messaage/message';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit{
  newUser: User = { nickName: '', fullName: '', status: Status.ONLINE };
  connectedUsers: User[] = [];

  constructor(private userService: UserService) {}
  ngOnInit(): void {

  }

  addUser(): void {
    this.userService.addUser(this.newUser).subscribe();
  }

  disconnectUser(user: User): void {
    this.userService.disconnectUser(user).subscribe();
  }

  getConnectedUsers(): void {
    this.userService.getConnectedUsers().subscribe(users => {
      this.connectedUsers = users;
    });
  }
}
