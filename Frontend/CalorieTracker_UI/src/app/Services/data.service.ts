import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class DataService {

  private collectedData: any = {};

  constructor() {}

  setData(key: string, value: any) {
    this.collectedData[key] = value;
  }

  getData(key: string): any {
    return this.collectedData[key];
  }
  getAllData(): any {
    return this.collectedData;
  }
  clearData() {
    this.collectedData = {};
  }
}
