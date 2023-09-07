import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Users } from '../models/users';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {
  content?: string;
  users?: Users[];
  currentUsers:Users={};
  currentIndex = -1;
  baseUrl = 'http://localhost:8080/api/test/getUsers';
  
  constructor(private userService: UserService,private http: HttpClient) { }

  ngOnInit(): void {
    this.userService.getAdminBoard().subscribe({
      next: data => {
        this.content = data;
      },
      error: err => {
        this.content = JSON.parse(err.error).message;
      }
    });
    this.loadUsers();
  }  
   getUsers()
   {
    return this.http.get<Array<Users>>("http://localhost:8080/api/test/users");
}
  private loadUsers() {
    this.getUsers().subscribe((data: Array<Users>) => {
            this.users = data; 
        });
}

setActiveTutorial(tutorial: Users, index: number): void {
  this.currentUsers = tutorial;
  this.currentIndex = index;
}

deleteAll(): Observable<any> {
  return this.http.delete(this.baseUrl);
}

removeAllTutorials(): void {
  this.deleteAll().subscribe({
    next: (res) => {
      console.log(res);
      this.refreshList();
    },
    error: (e) => console.error(e)
  });
}

refreshList(): void {
  this.loadUsers();
  this.currentUsers = {};
  this.currentIndex = -1;
}

delete(id: any): Observable<any> {
  return this.http.delete(`${this.baseUrl}/${id}`);
}

deleteTutorial(): void {
  this.delete(this.currentUsers.id).subscribe({
    next: (res) => {
      console.log(res);
      this.refreshList();
      //this.router.navigate(['/tutorials']);
    },
    error: (e) => console.error(e)
  });
}

}
