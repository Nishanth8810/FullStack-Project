import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-card-list',
  templateUrl: './card-list.component.html',
  styleUrl: './card-list.component.scss'
})
export class CardListComponent implements OnInit {
  block(arg0: any) {
    throw new Error('Method not implemented.');
  }
  unblock(arg0: any) {
    throw new Error('Method not implemented.');
  }
  cards: any[] = [];
  pageSize = 10;
  pageIndex = 0;
  totalItems = 0;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadCards();
  }

  loadCards() {
    const apiUrl = `http://localhost:8080/userDetail/paginated?page=${this.pageIndex}&size=${this.pageSize}`;
    this.http.get<any>(apiUrl).subscribe(data => {
      this.cards = data.content;
      this.totalItems = data.totalElements;
    });
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadCards();
  }
}
