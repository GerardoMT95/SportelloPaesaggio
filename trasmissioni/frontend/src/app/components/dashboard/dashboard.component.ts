import { ExampleService } from './../../services/example.service';
import { ApiService } from './../../services/api.service';
import { Component, OnInit } from '@angular/core';
import { LoadingService } from '../../services/loading.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(
    private loadingService:LoadingService,
    private exampleService:ExampleService
  ) { }

  ngOnInit() {
    setTimeout(()=>{
      this.loadingService.emitLoading(false);
    },100);
 
  }
  
  recuperaCountries(){
    this.exampleService.getCountries().subscribe(res => {
      console.log("Success", res);
    }, error => {
      console.log("Error", error);
    });
  }

}
