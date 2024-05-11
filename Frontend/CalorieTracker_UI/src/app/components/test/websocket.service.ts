import { Injectable } from '@angular/core';
import { Stomp, CompatClient } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ChatMessage } from './messaage/message';


@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient!: CompatClient;
  private messageSubject = new BehaviorSubject<ChatMessage | null>(null);

  constructor(private http: HttpClient) {
 
  }

  initializeWebSocketConnection(userid:any) {
    const socket = new SockJS('https://chat.calocoach.shop/ws');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      // Subscribe to public messages
      this.stompClient.subscribe('/user/public', (message) => {
        if (message.body) {
          const chatMessage: ChatMessage = JSON.parse(message.body);
          this.messageSubject.next(chatMessage);
        }
      });

      const privateChatDestination = `/user/${userid}/queue/messages`;
      this.stompClient.subscribe(privateChatDestination, (message) => {

        if (message.body) {          
          const privateMessage: ChatMessage = JSON.parse(message.body);
          this.messageSubject.next(privateMessage);
        }
      });
    });
  }
  
  addUser(addUserMessage: any) {
    this.stompClient.send('/app/user.addUser', {}, JSON.stringify(addUserMessage));
  }
  sendMessage(message: ChatMessage) {
    this.stompClient.send('/app/chat', {}, JSON.stringify(message));
  }

  getChatMessages(senderId: string, recipientId: string): Observable<ChatMessage[]> {
    return this.http.get<ChatMessage[]>(`https://app.calocoach.shop/messages/${senderId}/${recipientId}`);
  }
  
  // getChatMessages(senderId: string, recipientId: string): Observable<ChatMessage[]> {
  //   return this.http.get<ChatMessage[]>(`http://localhost:9090/messages/${senderId}/${recipientId}`);
  // }
  
 
  getMessageSubject(): Observable<ChatMessage | null> {
    return this.messageSubject.asObservable();
  }

  disconnect(addUserMessage:any): void {

    this.stompClient.send('/app/user.disconnectUser', {}, JSON.stringify(addUserMessage));
  }

  disconnectWebSocket() {
    if (this.stompClient) {
      this.stompClient.disconnect(() => {
        console.log('Disconnected from WebSocket');
      });
    }
  }

}
