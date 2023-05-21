import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GlobalService } from '../../services/global.service';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  routeParams;
  constructor(
    private activatedRoute:ActivatedRoute,
    private globalService:GlobalService
  ){ }

  ngOnInit() {
    this.routeParams = this.activatedRoute.snapshot.queryParams;
  }

  back():void{
    this.globalService.redirect();
  }
}
