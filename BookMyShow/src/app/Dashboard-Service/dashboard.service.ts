import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ChartDataResponse } from '../modal-interfaces/chart.model';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http:HttpClient) { }
   hostUrl = 'http://localhost:8000/dashboard/';
  public getDashboardData(cardTitle:string):Observable<any> | Observable<ChartDataResponse>{
    const url = this.hostUrl+cardTitle;
    const headers = new HttpHeaders();
    return this.http.get(url,{ headers,responseType:'json'});
  }

  
}
