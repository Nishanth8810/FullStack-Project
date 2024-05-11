import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { loadStripe } from '@stripe/stripe-js';
import { environment } from '../../models/environment';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../Services/auth.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.scss'
})
export class CheckoutComponent implements OnInit {
  stripePromise = loadStripe(environment.stripe);
  amount!:number;
  planName:string="plan";
  id!:any;
  constructor(private http: HttpClient,
   private route:ActivatedRoute,
   private auth:AuthService
    ) {}
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
       this.amount = +params['amount'],
       this.planName=params['planName'];
     this.id = this.auth.getUserId()

    this.pay();
       
  })
  }

  async pay(): Promise<void> {
    const payment = {
      name: this.planName,
      currency: 'inr',
      amount: this.amount*100,
      quantity: '1',
      cancelUrl: 'https://calorie-tracker-ui-3mez.vercel.app/rates-service',
      successUrl: 'https://calorie-tracker-ui-3mez.vercel.app/success',
    };
    // const payment = {
    //   name: this.planName,
    //   currency: 'inr',
    //   amount: this.amount*100,
    //   quantity: '1',
    //   cancelUrl: 'http://localhost:4200/rates-service',
    //   successUrl: 'http://localhost:4200/success',
    // };
    sessionStorage.setItem('planName', JSON.stringify(this.planName));
    sessionStorage.setItem('email', JSON.stringify(this.id));
    sessionStorage.setItem('amount', JSON.stringify(this.amount));

    const stripe = await this.stripePromise;

    this.http
  .post(`${environment.serverUrl}/payment`, payment)
  .subscribe(
    (data: any) => {    
      stripe!.redirectToCheckout({
        sessionId: data.id,
      });
    },
    (error) => {
      console.error('Payment failed:', error);
      // Handle the error here (e.g., display an error message to the user)
    }
  );
  }
}