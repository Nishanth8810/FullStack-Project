import { Component, OnInit } from '@angular/core';
import {  Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { AuthService } from '../../../Services/auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent implements OnInit {
logOut() {
throw new Error('Method not implemented.');
}
filterUsers() {
throw new Error('Method not implemented.');
}

searchTerm: any;
  constructor(private router:Router,
    private toast:NgToastService,
    private auth:AuthService,
    private http:HttpClient
    ){}
    content:any = [];
    currentPage: any = 1;
    itemsPerPage: any = "a";
    pagedContent: any = [];
    totalItems: any = "totoal";

  ngOnInit(): void {
  //   this.loadData();
  }

  // loadData(){
  //   this.auth.findAll().subscribe({
  //     next:(res:any)=>{
  //       this.content = res.content;
      
  //     }
  //   })
  // }
//  unblock(userId: any) {
//     this.auth.unblockUser(userId).subscribe({
//       next:(res)=>{
//         console.log(res);
//         this.loadData();
//       }
//     });
//   }
//   block(userId: any) {
//     this.auth.blockUser(userId).subscribe({
//       next:(res)=>{
//         console.log(res);
//         this.loadData();
//       }
//     });
 
//     }
//     searchUsers() {
//       throw new Error('Method not implemented.');
//       }
//       logOut() {
//         this.auth.logOut();
//         }


//      pageChanged(pageNumber: number) {
//           // Update the current page
//           this.currentPage = pageNumber;
//           console.log("asdpage",pageNumber);
          
//           // Update the pagedContent array with items for the current page
//           this.updatePagedContent();
//         }
      
//         // Update pagedContent array with items for the current page
//     updatePagedContent() {
//       console.log("asdpageasdasd");
//           const startIndex = (this.currentPage - 1) * this.itemsPerPage;
//           const endIndex = Math.min(startIndex + this.itemsPerPage, this.content.length);
//           this.pagedContent = this.content.slice(startIndex, endIndex);
//           console.log("paginated content",this.pagedContent);
//         }


//         loadUsers() {
//           // Calculate pagination offset
//           const offset = (this.currentPage - 1) * this.itemsPerPage;
          
//           // Fetch users from the server with pagination parameters
//           this.http.get<any[]>('http://localhost:8080/allUsers', {
//             params: {
//               searchTerm: this.searchTerm,
//               offset: offset.toString(),
//               limit: this.itemsPerPage.toString()
//             }
//           }).subscribe((response:any) => {
//             this.content = response;
//             // Assuming the server provides the total count of items
//             this.totalItems = response.totalCount;
//           });
//         }

}
