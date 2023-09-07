import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  content?: string;
  birthDay: Date = new Date();
  startTime: any = "";
  endTime: any = ""; 
  resultTime : any= "";
  constructor(private userService: UserService) { }

  userTimeSpend(start:any,end:any){
    const seconds= Math.round(Math.abs((start - end)/1000)); 
    const minutes = Math.floor(Math.abs((start - end)/60000)); 
      const hours = Math.floor(Math.abs((start - end) / 3600000));    
    let totalTime = (seconds >= 3600 ? hours + " hours  ":"")+(seconds >= 60 ?   minutes - (hours * 60) + " minutes  ":"")
    + (seconds - (minutes * 60)) + " seconds";
    console.log(totalTime);
  }

  ngOnInit(): void {
    
    this.userService.getPublicContent().subscribe({
      next: data => {
        this.content = data;
      },
      error: err => {
        this.content = JSON.parse(err.error).message;
      }
    });
  }
}
