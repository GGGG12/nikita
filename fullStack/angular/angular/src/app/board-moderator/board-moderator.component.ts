import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { HttpClient } from '@angular/common/http';
import { Users } from '../models/users';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-board-moderator',
  templateUrl: './board-moderator.component.html',
  styleUrls: ['./board-moderator.component.css']
})
export class BoardModeratorComponent implements OnInit {
  content?: string;
  private unitsUrl: string;

  constructor(private userService: UserService, private http: HttpClient) {
    this.unitsUrl = "http://localhost:8080/api/test/getAllStock";
   }

  ngOnInit(): void {
    this.userService.getModeratorBoard().subscribe({
      next: data => {
        this.content = data;
      },
      error: err => {
        this.content = JSON.parse(err.error).message;
      }
    });
  }
  // public getAll(user : Users): Observable<BoardModeratorComponent>{
  //   return this.http.get<Users>(this.unitsUrl);
  // }
}
