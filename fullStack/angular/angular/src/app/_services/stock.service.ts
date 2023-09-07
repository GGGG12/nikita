import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { stock } from '../models/stock.model';

const baseUrl = 'http://localhost:8080/api/test/getAll';

@Injectable({
  providedIn: 'root',
})
export class StockService {

  url = 'http://localhost:8080/api/test/getAllStock';
  constructor(private http: HttpClient) {

  }
  public getAllStock():Observable<stock[]>
  {
    return this.http.get<stock[]>(this.url);
  }

  // getAll(): Observable<stock[]> {
  //   return this.http.get<stock[]>(baseUrl);
  // }

  // get(id: any): Observable<stock> {
  //   return this.http.get<stock>(`${baseUrl}/${id}`);
  // }

  // create(data: any): Observable<any> {
  //   return this.http.post(baseUrl, data);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${baseUrl}/${id}`, data);
  // }

  // delete(id: any): Observable<any> {
  //   return this.http.delete(`${baseUrl}/${id}`);
  // }

  // deleteAll(): Observable<any> {
  //   return this.http.delete(baseUrl);
  // }

  // findByTitle(title: any): Observable<stock[]> {
  //   return this.http.get<stock[]>(`${baseUrl}?address=${title}`);

  // }
}
