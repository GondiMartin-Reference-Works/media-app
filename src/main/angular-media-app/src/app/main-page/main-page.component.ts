import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent implements OnInit{

  constructor(private appComponent: AppComponent) { }

  ngOnInit(): void {
    this.appComponent.getAllUser();
  }

}
