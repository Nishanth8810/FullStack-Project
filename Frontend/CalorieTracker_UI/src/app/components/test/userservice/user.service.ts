import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { WebSocketSubject } from 'rxjs/webSocket';
import { User } from '../messaage/message';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private wsUrl = 'ws://localhost:8088/ws'; // WebSocket URL
  private httpUrl = 'http://localhost:8088'; // HTTP base URL

  private ws$: WebSocketSubject<any>;

  constructor(private http: HttpClient) {
    this.ws$ = new WebSocketSubject(this.wsUrl);
  }

  addUser(user: User): Observable<User> {
    // Send WebSocket message to addUser endpoint
    this.ws$.next({ destination: '/app/user.addUser', body: user });
    return this.ws$.asObservable();
  }

  disconnectUser(user: User): Observable<User> {
    // Send WebSocket message to disconnectUser endpoint
    this.ws$.next({ destination: '/app/user.disconnectUser', body: user });
    return this.ws$.asObservable();
  }

  getConnectedUsers(): Observable<User[]> {
    // HTTP GET request to retrieve connected users
    return this.http.get<User[]>(`${this.httpUrl}/users`);
  }
}
