import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { stock } from '../models/stock.model';
import { StockService } from '../_services/stock.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { HttpClient } from '@angular/common/http';
import { ProfileComponent } from '../profile/profile.component';
import { Observable, Subscription, map, share, timer } from 'rxjs';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {
enteredValue: any;
get_radar_lifecycle_data() {
throw new Error('Method not implemented.');
}
  content?: string;
  birthDay: Date = new Date();
  currentUser: any;
  url ='http://localhost:8081/user';
  id:any;
  time = new Date();
  rxTime = new Date();
  subscription: Subscription = new Subscription;
  usAddress: Array<stock> = [];
  constructor(private userService: UserService,private token: TokenStorageService,private http: HttpClient
    ,private StockService:StockService) { }

  ngOnInit(): void {
    this.subscription = timer(0, 1000)
    .pipe(
      map(() => new Date()),
      share()
    )
    .subscribe(time => {
      this.rxTime = time;
    });

    this.userService.getUserBoard().subscribe({
      next: data => {
        this.content = data;
      },
      error: err => {
        this.content = JSON.parse(err.error).message;
      }
    });
    this.currentUser = this.token.getUser();
    this.StockService.getAllStock().subscribe(data=> {
      this.usAddress = data;
      console.log(data);}
      )
    //this.retrieveTutorials();
  }

  

  // get(id:any):Observable<ProfileComponent>{
    
  //   return this.http.get<ProfileComponent>(`${this.url}/${id}`)
  // }

  copyMessage(val: string){
    const selBox = document.createElement('textarea');
    selBox.style.position = 'fixed';
    selBox.style.left = '0';
    selBox.style.top = '0';
    selBox.style.opacity = '0';
    selBox.value = val;
    document.body.appendChild(selBox);
    selBox.focus();
    selBox.select();
    document.execCommand('copy');
    document.body.removeChild(selBox);
  }


//   retrieveTutorials(): void {
//     this.StockService.getAll().subscribe({
//       next: (data) => {
//         this.usAddress = data;
//         console.log(data);
//       },
//       error: (e) => console.error(e)
//     });
// }

}
