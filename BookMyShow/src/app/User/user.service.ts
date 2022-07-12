import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { UserDetails } from '../modal-interfaces/userDetails.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  apiUrl = 'http://localhost:8000/user/auth';
  public authenticateUser(userId:string,userPassword:string):Observable<UserDetails|any>{
    const reqBody = {
      'userId':userId,
      'userPassword':userPassword
    }
    const headers = new HttpHeaders();
    return this.http.post(this.apiUrl,reqBody,{headers,responseType:'json'});
  }
}
