import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';
import { PageEvent } from '@angular/material/paginator';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-trainer-view',
  templateUrl: './trainer-view.component.html',
  styleUrl: './trainer-view.component.scss'
})
export class TrainerViewComponent implements OnInit {
  Trainers: any;
  pageSize = 10;
  pageIndex = 0;
  totalItems = 0;
  sort:string="ASC"

  constructor(
    private auth: AuthService,
    private spinner: NgxSpinnerService

  ) { }

  ngOnInit(): void {
    this.spinner.show();
    this.getAllTrainer();
  }

  sortUsing(option:any){
    this.sort=option;
    this.getAllTrainer();
  }
  getAllTrainer() {
    this.auth.getAllTrainers(this.pageSize,this.pageIndex,this.sort).subscribe((res) => {
      this.Trainers = res.content;
      this.totalItems = res.totalElements;
      this.spinner.hide();
    })
  }

  unblock(userId: any) {

    this.auth.unblockUser(userId).subscribe({
      next: (res) => {

        this.getAllTrainer();
      }
    });
  }


  block(userId: any) {
    this.auth.blockUser(userId).subscribe({
      next: (res) => {
        this.getAllTrainer();
      }
    });
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getAllTrainer();
  }

}
