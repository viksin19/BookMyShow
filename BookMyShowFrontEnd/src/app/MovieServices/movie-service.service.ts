import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { RecomendedMovieList } from '../home/home.component';
import { MovieList, TheatreData } from '../modal-interfaces/movieList.model';
import { UserInfo } from '../modal-interfaces/userDetails.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private apiUrl = `${environment.apiBaseUrl}/movie/`;
  movieList: RecomendedMovieList[] = []

  isAuthenticated: boolean = false;
  userInfo: UserInfo | any;

  cityUrlMap: Map<string, string> = new Map();
  
  constructor(private http: HttpClient) {
    this.initializeCityMap();
  }

  movieTableData: MovieList[] = []

  /**
   * Initialize city to URL mapping.
   */
  private initializeCityMap(): void {
    this.cityUrlMap.set('Mumbai', '//in.bmscdn.com/m6/images/common-modules/regions/mumbai.png');
    this.cityUrlMap.set('Bengaluru', '//in.bmscdn.com/m6/images/common-modules/regions/bang.png');
    this.cityUrlMap.set('Chennai', '//in.bmscdn.com/m6/images/common-modules/regions/chen.png');
    this.cityUrlMap.set('Delhi', '//in.bmscdn.com/m6/images/common-modules/regions/ncr.png');
    this.cityUrlMap.set('Hyderabad', '//in.bmscdn.com/m6/images/common-modules/regions/hyd.png');
    this.cityUrlMap.set('Pune', '//in.bmscdn.com/m6/images/common-modules/regions/pune.png');
    this.cityUrlMap.set('Ahmedabad', '//in.bmscdn.com/m6/images/common-modules/regions/ahd.png');
  }

  public getMovieList(): RecomendedMovieList[] {
    return this.movieList;
  }

  public getisAuthenticated(): boolean {
    return this.isAuthenticated
  }

  public setIsAuthenticated(value: boolean): void {
    this.isAuthenticated = value;
  }

  public setUserInfo(role: string): void {
    this.userInfo = {
      userRole: role,
      role: (role == 'admin') ? 0 : 1
    }
  }

  public getUserInfo(): UserInfo | any {
    return this.userInfo;
  }

  /**
   * Get movie data by context.
   * @param context API endpoint context
   * @returns Observable of movie list
   */
  public getMovieData(context: string): Observable<MovieList[] | Observable<Array<RecomendedMovieList>> | any> {
    if (!context) {
      throw new Error('Context parameter is required');
    }

    const url = this.apiUrl + context;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.get(url, { headers, responseType: 'json' });
  }

  /**
   * Get city to URL mapping for locations.
   * @param locationList List of location objects
   * @returns Array of city objects with mapped URLs
   */
  public getCityUrlMapData(locationList: any[] | undefined): any[] {
    const cityToUrlList: Array<any> = [];

    if (!locationList) {
      return cityToUrlList;
    }

    locationList.forEach(location => {
      cityToUrlList.push({
        'locationId': location?.locationId,
        'city': location?.city,
        'url': this.cityUrlMap.get(location?.city)
      })
    });

    return cityToUrlList;
  }

  /**
   * Get movie theatres for a specific movie and location.
   * @param context API endpoint context
   * @param movieId Movie identifier
   * @param locationId Location identifier
   * @returns Observable of theatre data
   */
  public getMovieTheatre(context: string, movieId: number, locationId: number): Observable<any> {
    if (!context || !movieId || !locationId) {
      throw new Error('Context, movieId, and locationId are required');
    }

    const url = this.apiUrl + context;
    const reqBody = {
      'movieId': movieId,
      'locationId': locationId
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(url, reqBody, { headers, responseType: 'json' });
  }
}
