import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../Services/auth.service';
import { ConnectionService } from '../../../_user-services/connection.service';
import { NgToastService } from 'ng-angular-popup';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-trainer-detail',
  templateUrl: './trainer-detail.component.html',
  styleUrl: './trainer-detail.component.scss'
})
export class TrainerDetailComponent implements OnInit{

  trainerId:any
  trainerdata:any
  requestText:string="Request connection"
  bool:boolean=false;
  senderId:any;
  constructor(
    private route:ActivatedRoute,
    private auth:AuthService,
    private connection:ConnectionService,
    private toast:NgToastService,
    private spinner: NgxSpinnerService,

  ){}
  ngOnInit(): void {
    this.spinner.show();
    this.route.params.subscribe(params => {
      this.trainerId = +params['id']; 
      this.loadtrainerById();
    });
    this.loadStatus();
  }
  loadtrainerById(){
    this.auth.getTrainerById(this.trainerId).subscribe((res)=>{

      this.trainerdata=res;
      this.spinner.hide();
    })
    
    this.senderId=localStorage.getItem('userId');
  }
  loadStatus(){
    this.connection.getStatus(this.trainerId).subscribe((res)=>{

      
     if(res==="IM_USED"){
      this.requestText="Pending";
      this.bool=true;
     }else if(res==="ACCEPTED"){
      this.requestText="Message";
      this.bool=true;
     }else if(res==="ALREADY_REPORTED"){
      this.requestText="Request rejected";
      this.bool=true;
     }
    })
    
  }
  sendRequest(id: any) {
    this.connection.sendRequest(id).subscribe((res)=>{
      if(res==="CREATED"){
        this.bool=true;
        this.loadStatus();
      }else{
        this.toast.error({detail:"error",summary:"Something went wrong"})
      }
     
    })
    }

}
