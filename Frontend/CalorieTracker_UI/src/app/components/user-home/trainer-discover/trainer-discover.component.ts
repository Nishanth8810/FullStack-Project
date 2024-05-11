import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import {  NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-trainer-discover',
  templateUrl: './trainer-discover.component.html',
  styleUrl: './trainer-discover.component.scss'
})
export class TrainerDiscoverComponent implements OnInit {
  cards: any[] = [];
  pageSize = 5;
  pageIndex = 0;
  totalItems = 0;

  Trainers: any;

  constructor(
    private authService: AuthService,
    private spinner:NgxSpinnerService,

    private router:Router
  ) {}

  ngOnInit(): void {
    this.getAllTrainer();
  }

  getAllTrainer() {
    this.spinner.show();
    this.authService.getAllTrainers(this.pageSize, this.pageIndex, 'ASC').subscribe((res: any) => {
      console.log("ASdad",res);
      
      this.spinner.hide();
      this.Trainers = res.content;
      this.cards = res.content;
      this.totalItems = res.totalElements;
      this.scrollToTop();

      this.router.navigate([], {
        queryParams: {
          page: this.pageIndex,
          size: this.pageSize
        },
        queryParamsHandling: 'merge', 
        replaceUrl: true 
      });
    });
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getAllTrainer();
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
