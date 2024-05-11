import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Recipe } from '../models/recipe.model';
import { UserServiceService } from '../Services/user-service.service';
import { Observable, switchMap } from 'rxjs';
import { AuthService } from '../Services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(
    private http:HttpClient,
    private userStore:UserServiceService,
    private auth:AuthService
  ) { }
  private baseUrl:String= "https://api.calocoach.shop/recipes/";
  private baseUrl1:String= "https://api.calocoach.shop/";
  private baseUrlPlan:String= "https://api.calocoach.shop/user/";


  // private baseUrl:String= "http://localhost:9090/recipes/";
  // private baseUrl1:String= "http://localhost:9090/";
  // private baseUrlPlan:String= "http://localhost:9090/user/";



  fetchRecipesByCalorie(start: number, end: number) {
   return this.http.get<any>(`${this.baseUrl1}recipes/getRecipeByCalorieBetween?start=${start}&end=${end}`)
  }
  

saveRecipe(recipe: Recipe, imageFile: File) {
  const formData: FormData = new FormData();
  formData.append('recipeDTO', JSON.stringify(recipe));
  formData.append('imageFile', imageFile);
  return this.http.post<any>(this.baseUrl + 'save', formData);
}
getAllRecipe() {
  return this.http.get<any>(`${this.baseUrl}allRecipes`,{});
 }
 getAllRecipePaginated(pageSize: number, pageIndex: number) {
  return this.http.get<any>(`${this.baseUrl}allRecipesPaginated?page=${pageIndex}&size=${pageSize}`);
}
getById(id:number){
  return this.http.get<any>(`${this.baseUrl}food/${id}`,{});
}
saveRecipeUser(recipeid:number):Observable<any> {
  let id=this.auth.getUserId()
  return this.userStore.getIdFromStore().pipe(
    switchMap((res: string) => {
      const data={
        user:id,
        recipeId:recipeid
      }
      return this.http.post<any>(`${this.baseUrl1}recipes/saveRecipe`,data);
    })
  );
}
searchRecipes(searchQuery: string) {

  return this.http.get<any>(`${this.baseUrl}allRecipe?searchQuery=${searchQuery}`,{});
}

unlistRecipe(id: number) {
  return this.http.put<any>(`${this.baseUrl}un-list/${id}`,{});
}
listRecipe(id: number) {
  return this.http.put<any>(`${this.baseUrl}list/${id}`,{});
}

listPlan(id: number) {
  return this.http.put<any>(`${this.baseUrlPlan}plan/list/${id}`,{});
}
unlistPlan(id: number) {
  return this.http.put<any>(`${this.baseUrlPlan}plan/unList/${id}`,{});
}



reportRecipe(recipeId: any,reason:string) {
  let email=this.auth.getEmail();

  const data={
    reason:reason,
    userEmail:email,
    recipeId:recipeId
  }
  return this.http.post<any>(`${this.baseUrl}recipeReport`,data);
}
editRecipe(recipe: Recipe, imageFile: File) {
  const formData: FormData = new FormData();
  formData.append('recipeDTO', JSON.stringify(recipe));
  formData.append('imageFile', imageFile);  
  return this.http.post<any>(this.baseUrl + 'edit', formData);
}

getAllPlan(){
  return this.http.get<any>(this.baseUrlPlan + 'getAllPlan' );
}

savePlan(value: any) {
  return this.http.post<any>(this.baseUrlPlan + 'save' ,value);
}


loadSavedRecipeOfUser() {
  let email=this.auth.getEmail();
  return this.http.get<any>(this.baseUrl1 + `userDetail/getSavedRecipes/${email}`);
}

deleteFromSaved(savedId:number): Observable<any> {
  return this.http.delete<any>(this.baseUrl1 + `recipes/deleteSavedRecipe/${savedId}`);
}


}
