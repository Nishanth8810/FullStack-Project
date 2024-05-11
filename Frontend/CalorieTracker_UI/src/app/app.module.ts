import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { MatInputModule, } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatStepperModule } from '@angular/material/stepper';
import { MatRadioModule } from '@angular/material/radio';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { NgToastModule } from 'ng-angular-popup';
import { tokenInterceptor } from './_intercepters/token.interceptor';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { TrainerDashboardComponent } from './components/trainer-home/trainer-dashboard/trainer-dashboard.component';

import { PreSetDietComponent } from './components/user-register/pre-set-diet/pre-set-diet.component';
import { ExclusionsComponent } from './components/user-register/exclusions/exclusions.component';
import { GoalComponent } from './components/user-register/goal/goal.component';
import { InformationComponent } from './components/user-register/information/information.component';
import { NutritionTargetComponent } from './components/user-register/nutrition-target/nutrition-target.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { TrainerExperienceComponent } from './components/trainer-register/trainer-experience/trainer-experience.component';
import { RatesServicesComponent } from './components/trainer-register/rates-services/rates-services.component';
import { NgOtpInputModule } from 'ng-otp-input';
import { NgxPaginationModule } from 'ngx-pagination';
import { UserSettingComponent } from './components/user-home/user-setting/user-setting.component';
import { ReipeAddComponent } from './components/admin/recipe-add/recipe-add.component';

import { HeaderComponent } from './components/admin/header/header.component';
import { UserViewComponent } from './components/admin/user-view/user-view.component';
import { RecipeDiscoverComponent } from './components/user-home/recipe-discover/recipe-discover.component';
import { UserDashboardComponent } from './components/user-home/user-dashboard/user-dashboard.component';
import { FoodComponent } from './components/user-home/food/food.component';
import { RecipeManageComponent } from './components/admin/recipe-manage/recipe-manage.component';
import { RecipeEditComponent } from './components/admin/recipe-edit/recipe-edit.component';
import { OtpComponent } from './components/otp-components/otp/otp.component';
import { ResetComponent } from './components/otp-components/reset/reset.component';
import { ResetPassordOTPComponent } from './components/otp-components/reset-passord-otp/reset-passord-otp.component';
import { CancelComponent } from './payment/cancel/cancel.component';
import { SuccessComponent } from './payment/success/success.component';
import { CheckoutComponent } from './payment/checkout/checkout.component';
import { TrainerDiscoverComponent } from './components/user-home/trainer-discover/trainer-discover.component';
import { TrainerDetailComponent } from './components/user-home/trainer-detail/trainer-detail.component';
import { TrainerViewComponent } from './components/admin/trainer-view/trainer-view.component';
import { PlanManagementComponent } from './components/admin/plan-management/plan-management.component';
import { CardComponent } from './components/cardss/card/card.component';
import { CardListComponent } from './components/cardss/card-list/card-list.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { NavbarUserComponent } from './components/user-home/navbar-user/navbar-user.component';
import { PhysicalStatsComponent } from './components/user-home/physical-stats/physical-stats.component';
import { TrainerSettingsComponent } from './components/trainer-home/trainer-settings/trainer-settings.component';
import { ManageClientsComponent } from './components/trainer-home/manage-clients/manage-clients.component';
import { MealPlanComponent } from './components/trainer-home/meal-plan/meal-plan.component';
import { MatOption, MatSelect } from '@angular/material/select';
import { CreateMealPlanComponent } from './components/trainer-home/create-meal-plan/create-meal-plan.component';
import { PlannerComponent } from './components/user-home/planner/planner.component';
import { MealPlanListComponent } from './components/user-home/meal-plan-list/meal-plan-list.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { MessaageComponent } from './components/test/messaage/messaage.component';
import { UserComponent } from './components/test/userservice/user/user.component';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    AdminDashboardComponent,
    TrainerDashboardComponent,
    OtpComponent,
    ResetComponent,
    ResetPassordOTPComponent,
    PreSetDietComponent,
    ExclusionsComponent,
    GoalComponent,
    InformationComponent,
    NutritionTargetComponent,
    TrainerExperienceComponent,
    RatesServicesComponent,
    UserSettingComponent,

    ReipeAddComponent,
    HeaderComponent,
    UserViewComponent,
    RecipeDiscoverComponent,
    UserDashboardComponent,
    FoodComponent,
    RecipeManageComponent,
    RecipeEditComponent,
    CancelComponent,
    SuccessComponent,
    CheckoutComponent,
    TrainerDiscoverComponent,
    TrainerDetailComponent,
    TrainerViewComponent,
    PlanManagementComponent,
    CardComponent,
    CardListComponent,
    NavbarUserComponent,
    PhysicalStatsComponent,
    TrainerSettingsComponent,
    ManageClientsComponent,
    MealPlanComponent,
    CreateMealPlanComponent,
    PlannerComponent,
    MealPlanListComponent,
    MessaageComponent,
    UserComponent,


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgToastModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatStepperModule,
    MatRadioModule,
    NgOtpInputModule,
    NgxPaginationModule,
    MatCardModule,
    MatPaginatorModule,
    MatSelect,
    MatOption,
    NgxSpinnerModule,

  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch(),
    withInterceptors([tokenInterceptor])),
    provideAnimationsAsync('noop'),
    DatePipe

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
