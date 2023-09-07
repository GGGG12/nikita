import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Users } from '../models/users';

const API_URL = 'http://localhost:8080/api/test/';
const baseUrl = 'http://localhost:8080/api/test/getUsers';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }
  

  public getUsers(id:any):Observable<Users[]>
  {
    return this.http.get<Users[]>(`${baseUrl}/${id}`);
  }
  
  getUser(id:any):Observable<Users>{
    
    return this.http.get<Users>('http://localhost:8080/api/test/getUsers/' + id);

  }

  public save(user: Users) {
    return this.http.post<Users>('http://localhost:8080/api/test/profile', user);
  }

}
