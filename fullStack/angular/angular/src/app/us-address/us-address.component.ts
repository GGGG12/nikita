import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { stock } from '../models/stock.model';
import { StockService } from 'src/app/_services/stock.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { CommonModule } from '@angular/common'
@Component({
  selector: 'app-us-address',
  templateUrl: './us-address.component.html',
  styleUrls: ['./us-address.component.css']
  
})
export class UsAddressComponent{
  baseUrl = 'http://localhost:8080/api/test/getAllStock';
  usAddress?: stock[];
  //currentTutorial: Tutorial = {};
  currentIndex = -1;
  adrress = '';
  test:any;
  editedUser: stock|null = null;
  
  constructor(private stock: StockService ,private http: HttpClient) { }
  

  ngOnInit(): void {
    //this.retrieveTutorials();
    //this.loadUsers();
  }
  // retrieveTutorials(): void {
  //   this.stock.getAll().subscribe({
  //     next: (data) => {
  //       this.usAddress = data;
  //       this.test=data;
  //       console.log(data);
  //     },
  //     error: (e) => console.error(e)
  //   });
    
  // }
  @ViewChild('readOnlyTemplate', {static: false}) readOnlyTemplate: TemplateRef<any>|undefined;
  @ViewChild('editTemplate', {static: false}) editTemplate: TemplateRef<any>|undefined;
  getUsers(){
    return this.http.get<Array<stock>>(this.baseUrl);
}
loadTemplate(stock: stock) {
  if (this.editedUser && this.editedUser.id === stock.id) {
      return this.editTemplate;
  } else {
      return this.readOnlyTemplate;
  }
}

  setActiveTutorial(stock: stock, index: number): void {
    //this.currentTutorial = tutorial;
    this.currentIndex = index;
  }
  private loadUsers() {
    this.getUsers().subscribe((data: Array<stock>) => {
            this.usAddress = data; 
        });
}
  refreshList(): void {
    //this.retrieveTutorials();
    //this.currentTutorial = {};
    this.currentIndex = -1;
  }


}
