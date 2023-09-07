import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { StockService } from 'src/app/_services/stock.service';
import { stock } from '../models/stock.model';
import { UserService } from '../_services/user.service';
import { Users } from '../models/users';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
  birthDay: Date = new Date();
  currentUser: any;
  testUser: Users = new Users;
  url ='http://localhost:8081/profile';
  id:any;
  usAddress?: stock[];
  detailUser: Users;
  detailsUser: Array<Users> =[];
 
  constructor(private token: TokenStorageService,private http: HttpClient,private userService:UserService ) 
  {
    this.detailUser = new Users();
  }

  // get(id:any):Observable<ProfileComponent>{
    
  //   return this.http.get<ProfileComponent>(`http://localhost:8080/api/test/getUsers/${id}`)

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

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.userService.getUser(this.currentUser.id).subscribe((data:Users)=> {
      this.detailUser = data;
      console.log(data);}
      )
  
  }
  editUser(user: Users){
    this.testUser=user;
  
  }
  onSubmit() {
    
    this.userService.save(this.detailUser).subscribe((data:Users) =>
      this.detailUser);
   
    location.reload();
    
  }
//   retrieveTutorials(): void {
//     this.stock.getAll().subscribe({
//       next: (data) => {
//         this.usAddress = data;
//         console.log(data);
//       },
//       error: (e) => console.error(e)
//     });
// }
}
