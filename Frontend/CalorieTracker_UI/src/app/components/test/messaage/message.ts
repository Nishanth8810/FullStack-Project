export interface ChatMessage {
  id: string;
  senderId: string;
  recipientId: string;
  content: string;
  timestamp: any;
}

export interface User {
  nickName: string;
  fullName: string;
  status: Status;
}

export enum Status {
  ONLINE = 'ONLINE',
  OFFLINE = 'OFFLINE'
}