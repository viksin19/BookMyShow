import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { RecomendedMovieList } from '../home/home.component';
import { MovieList, TheatreData } from '../modal-interfaces/movieList.model';
import { UserInfo } from '../modal-interfaces/userDetails.model';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private apiUrl = "http://localhost:8000/movie/";
  movieList : RecomendedMovieList[] = []
  
  isAuthenticated:boolean = false;
  userInfo:UserInfo | any;
  
  cityUrlMap: Map<string, string> = new Map();
  constructor(private http:HttpClient) { 
    this.cityUrlMap.set('Mumbai','//in.bmscdn.com/m6/images/common-modules/regions/mumbai.png');
    this.cityUrlMap.set('Bengaluru','//in.bmscdn.com/m6/images/common-modules/regions/bang.png');
    this.cityUrlMap.set('Chennai','//in.bmscdn.com/m6/images/common-modules/regions/chen.png');
    this.cityUrlMap.set('Delhi','//in.bmscdn.com/m6/images/common-modules/regions/ncr.png');
    this.cityUrlMap.set('Hyderabad','//in.bmscdn.com/m6/images/common-modules/regions/hyd.png');
    this.cityUrlMap.set('Pune','//in.bmscdn.com/m6/images/common-modules/regions/pune.png');
    this.cityUrlMap.set('Ahmedabad','//in.bmscdn.com/m6/images/common-modules/regions/ahd.png');
  }
  
  movieTableData:MovieList[] = []


  public getMovieList(){
    return this.movieList;
  }

  public getisAuthenticated():boolean{
      return this.isAuthenticated
  }
  public setIsAuthenticated(value:boolean){
      this.isAuthenticated = value;
  }
  
  public setUserInfo(role:string){
    this.userInfo = {
      userRole:role,
      role:(role=='admin')?0:1
  }
  }
  public getUserInfo():UserInfo|any{
    return this.userInfo;
  }
  public getMovieData(context:string):Observable<MovieList[]|Observable<Array<RecomendedMovieList>>|any>{
    const url = this.apiUrl+context;
    const headers = new HttpHeaders();
    return this.http.get(url,{headers,responseType:'json'});
   // return this.movieTableData;
  }
  public getCityUrlMapData(locationList:any[]|undefined){
    //const cityList = ['Mumbai','Bengaluru','Chennai','Delhi','Pune'];
    //cityList.sort();
    const cityToUrlList:Array<any> = [];
    locationList?.forEach(location=>{
      cityToUrlList.push({
        'locationId':location?.locationId,
        'city':location?.city,
        'url':this.cityUrlMap.get(location?.city)
      })
    });

    return cityToUrlList;
  }
  public getMovieTheatre(context:string,movieId:number,locationId:number):Observable<any>{
    const url = this.apiUrl+context;
    const reqBody = {
      'movieId':movieId,
      'locationId':locationId
    }
    console.log(reqBody);
    const headers = new HttpHeaders();
    return this.http.post(url,reqBody,{headers,responseType:'json'});
  }
}
