import { NgModule } from '@angular/core';
import { ServerModule } from '@angular/platform-server';

import { AppModule } from './app.module';
import { AppComponent } from './app.component';
import {  NgxSpinnerModule } from 'ngx-spinner';

@NgModule({
  imports: [
    AppModule,
    ServerModule,
    NgxSpinnerModule.forRoot({ type: 'square-jelly-box' })
  ],
  bootstrap: [AppComponent],
})
export class AppServerModule {}
