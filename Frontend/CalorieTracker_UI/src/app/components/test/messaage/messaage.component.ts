import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebsocketService } from '../websocket.service';
import { ChatMessage } from './message';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';



@Component({
  selector: 'app-messaage',
  templateUrl: './messaage.component.html',
  styleUrl: './messaage.component.scss'
})
export class MessaageComponent implements OnInit, OnDestroy {
  message: string = '';
  chatMessages: ChatMessage[] = [];
  newMessage!: string;
  recipentId: any;
  senderId: any;
  username: any = 'bob';
  fullname: string = 'nishanth';
  websocketSubscription: any;



  constructor(private webSocketService: WebsocketService,
    private datePipe: DatePipe,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.websocketSubscription = this.webSocketService.getMessageSubject().subscribe((message: any) => {
      if (message) {
        if (message.content !== null && message.content !== undefined) {
          this.chatMessages.push(message);
        }
      }
    });
    this.route.paramMap.subscribe(params => {
      const senderId = params.get('senderId');
      const recipientId = params.get('recipientId');
      this.senderId = senderId;
      this.recipentId = recipientId;
    });
    this.addFirst();
    this.addUser();
   
  }


  loadChatMessages(senderId: string, recipientId: string): void {
    this.webSocketService.getChatMessages(senderId, recipientId)
      .subscribe(messages => {
        this.chatMessages = messages;
        console.log("asdasdasdasdasda")
      });
  }
  addFirst() {
    this.webSocketService.initializeWebSocketConnection(this.senderId);
    this.loadChatMessages(this.senderId, this.recipentId);
  }

  addUser(): void {
    const addUser = {
      nickName: this.senderId,
    };
    this.webSocketService.addUser(addUser);

  }

  sendMessage(): void {
    if (this.newMessage.trim() !== '') {
      const timestamp = this.datePipe.transform(new Date(), 'yyyy-MM-dd HH:mm:ss') || '';
      const chatMessage: ChatMessage = {
        id: '',
        senderId: this.senderId,
        recipientId: this.recipentId,
        content: this.newMessage.trim(),
        timestamp: timestamp
      };

      this.webSocketService.sendMessage(chatMessage);
      this.chatMessages.push(chatMessage);
      this.newMessage = '';
    }
  }

  disconncet() {
    const addUser = {
      nickName: this.senderId,
      status: 'OFFLINE'
    };
    this.webSocketService.disconnect(addUser)
    this.websocketSubscription.unsubscribe();
    this.webSocketService.disconnectWebSocket();
  }
  ngOnDestroy() {

    this.websocketSubscription.unsubscribe();
    this.webSocketService.disconnectWebSocket();
  }
}