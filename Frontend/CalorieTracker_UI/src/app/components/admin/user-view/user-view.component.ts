import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';
import { PageEvent } from '@angular/material/paginator';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrl: './user-view.component.scss'
})
export class UserViewComponent implements OnInit {

  content:any ;
  pageSize = 10;
  pageIndex = 0;
  totalItems = 0;
  sort:string="ASC"
  constructor(
    private auth:AuthService,
    private spinner: NgxSpinnerService
  ){}


  ngOnInit(): void {
    this.spinner.show();
    this.loadData();
  }

  loadData(){
    this.auth.findAll(this.pageSize,this.pageIndex,this.sort).subscribe({
      next:(res:any)=>{

        this.content = res.content;
        this.totalItems = res.totalElements;
        this.spinner.hide()
      }
    
    })
  }
  sortUsing(option:any){
    this.sort=option;
    this.loadData();
  }
  filterByUploadDate(filterOption: string): void {
    switch (filterOption) {
      case 'LAST_HOUR':
         this.auth.getFilter('LAST_HOUR').subscribe((res)=>{

         })
        break;
      case 'TODAY':
        this.auth.getFilter('TODAY').subscribe((res)=>{

         })
        break;
      case 'THIS_WEEK':
        this.auth.getFilter('THIS_WEEK').subscribe((res)=>{

         })
        break;
        case 'THIS_MONTH':
          this.auth.getFilter('THIS_MONTH').subscribe((res)=>{

           })
          break;
      // Add more cases for additional filter options
      default:
        this.auth.getFilter('LAST_HOUR').subscribe((res:any)=>{

          this.content = res.content;
          this.totalItems = res.totalElements;
         })
        break;
    }
  }
  unblock(userId: any) {

        this.auth.unblockUser(userId).subscribe({
          next:(res)=>{
            this.loadData();
          }
        });
      }
      
      block(userId: any) {
        this.auth.blockUser(userId).subscribe({
          next:(res)=>{
            this.loadData();
          }
        });
      }
      onPageChange(event: PageEvent) {
        this.pageIndex = event.pageIndex;
        this.pageSize = event.pageSize;
        this.loadData();
      }
    }

