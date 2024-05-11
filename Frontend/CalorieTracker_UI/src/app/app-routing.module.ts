import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { adminGuard, authGuard, isBlockedAuthorizedGuard, unAuthorizedGuard } from './_guards/auth.guard';
import { TrainerDashboardComponent } from './components/trainer-home/trainer-dashboard/trainer-dashboard.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { PreSetDietComponent } from './components/user-register/pre-set-diet/pre-set-diet.component';
import { ExclusionsComponent } from './components/user-register/exclusions/exclusions.component';
import { GoalComponent } from './components/user-register/goal/goal.component';
import { InformationComponent } from './components/user-register/information/information.component';
import { TrainerExperienceComponent } from './components/trainer-register/trainer-experience/trainer-experience.component';
import { RatesServicesComponent } from './components/trainer-register/rates-services/rates-services.component';
import { ReipeAddComponent } from './components/admin/recipe-add/recipe-add.component';
import { UserViewComponent } from './components/admin/user-view/user-view.component';
import { UserDashboardComponent } from './components/user-home/user-dashboard/user-dashboard.component';
import { FoodComponent } from './components/user-home/food/food.component';
import { RecipeDiscoverComponent } from './components/user-home/recipe-discover/recipe-discover.component';
import { RecipeManageComponent } from './components/admin/recipe-manage/recipe-manage.component';
import { RecipeEditComponent } from './components/admin/recipe-edit/recipe-edit.component';
import { OtpComponent } from './components/otp-components/otp/otp.component';
import { ResetComponent } from './components/otp-components/reset/reset.component';
import { ResetPassordOTPComponent } from './components/otp-components/reset-passord-otp/reset-passord-otp.component';
import { CheckoutComponent } from './payment/checkout/checkout.component';
import { CancelComponent } from './payment/cancel/cancel.component';
import { SuccessComponent } from './payment/success/success.component';
import { TrainerDiscoverComponent } from './components/user-home/trainer-discover/trainer-discover.component';
import { TrainerViewComponent } from './components/admin/trainer-view/trainer-view.component';
import { PlanManagementComponent } from './components/admin/plan-management/plan-management.component';
import { TrainerDetailComponent } from './components/user-home/trainer-detail/trainer-detail.component';
import { UserSettingComponent } from './components/user-home/user-setting/user-setting.component';
import { CardComponent } from './components/cardss/card/card.component';
import { CardListComponent } from './components/cardss/card-list/card-list.component';
import { PhysicalStatsComponent } from './components/user-home/physical-stats/physical-stats.component';
import { ManageClientsComponent } from './components/trainer-home/manage-clients/manage-clients.component';
import { MealPlanComponent } from './components/trainer-home/meal-plan/meal-plan.component';
import { TrainerSettingsComponent } from './components/trainer-home/trainer-settings/trainer-settings.component';
import { CreateMealPlanComponent } from './components/trainer-home/create-meal-plan/create-meal-plan.component';
import { PlannerComponent } from './components/user-home/planner/planner.component';
import { MealPlanListComponent } from './components/user-home/meal-plan-list/meal-plan-list.component';
import { MessaageComponent } from './components/test/messaage/messaage.component';
import { UserComponent } from './components/test/userservice/user/user.component';


const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  // {path:'login',component:LoginComponent,canActivate:[unAuthorizedGuard]},
  // {path:'user-view',component:UserViewComponent,canActivate:[authGuard]},
  // {path:'signup',component:SignupComponent,canActivate:[unAuthorizedGuard]},
  // {path:'trainer',component:TrainerDashboardComponent,canActivate:[isBlockedAuthorizedGuard]},
  // {path:'admin',component:AdminDashboardComponent,canActivate:[adminGuard,isBlockedAuthorizedGuard]},

  { path: 'login', component: LoginComponent },
  { path: 'user-view', component: UserViewComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'trainer-dashboard', component: TrainerDashboardComponent },
  { path: 'admin', component: AdminDashboardComponent },

  { path: 'otp', component: OtpComponent },
  { path: 'reset', component: ResetComponent },
  { path: 'resetOTP', component: ResetPassordOTPComponent },
  { path: 'pre-set', component: PreSetDietComponent },
  { path: 'exclusion', component: ExclusionsComponent },
  { path: 'goal', component: GoalComponent },

  // {path:'personal-information',component:InformationComponent,canActivate:[unAuthorizedGuard]},
  // {path:'experience',component:TrainerExperienceComponent,canActivate:[unAuthorizedGuard]},
  // {path:'recipe-add',component:ReipeAddComponent,canActivate:[authGuard]},
  // {path:'user-dashboard',component:UserDashboardComponent,canActivate:[authGuard]},
  // {path:'food/:id',component:FoodComponent,canActivate:[isBlockedAuthorizedGuard]},
  // {path:'recipe-discover',component:RecipeDiscoverComponent,canActivate:[authGuard]},
  // {path:'recipe-manage',component:RecipeManageComponent,canActivate:[adminGuard,isBlockedAuthorizedGuard]},

  { path: 'personal-information', component: InformationComponent },
  { path: 'experience', component: TrainerExperienceComponent },
  { path: 'user-dashboard', component: UserDashboardComponent },
  { path: 'recipe-add', component: ReipeAddComponent },
  { path: 'recipe-discover', component: RecipeDiscoverComponent },
  { path: 'food/:id', component: FoodComponent },
  { path: 'recipe-manage', component: RecipeManageComponent },

  { path: 'rates-service', component: RatesServicesComponent },
  { path: 'recipe-edit', component: RecipeEditComponent },
  { path: 'recipe-edit/:id', component: RecipeEditComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'cancel', component: CancelComponent },
  { path: 'success', component: SuccessComponent },
  { path: 'trainer-discover', component: TrainerDiscoverComponent },
  { path: 'trainer-detail/:id', component: TrainerDetailComponent },
  { path: 'admin-trainer-view', component: TrainerViewComponent },
  { path: 'plan-management', component: PlanManagementComponent },
  { path: 'savedRecipe', component: UserSettingComponent },
  { path: 'users', component: CardComponent },
  { path: 'usersAll', component: CardListComponent },
  { path: 'physical-stats', component: PhysicalStatsComponent },
  { path: 'planner/:id', component: PlannerComponent },
  { path: 'meal-plan-list', component: MealPlanListComponent },



   ///Trainer
  { path: 'manage-clients', component: ManageClientsComponent },
  { path: 'meal-plan', component: MealPlanComponent },
  { path: 'manage-clients', component: ManageClientsComponent },
  { path: 'trainer-setting', component: TrainerSettingsComponent },
  { path: 'create-mealPlan', component: CreateMealPlanComponent },
  { path: 'chat', component: MessaageComponent },
  { path:'userser',component:UserComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
