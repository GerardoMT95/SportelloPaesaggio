import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-circle',
  templateUrl: './circle.component.html',
  styleUrls: ['./circle.component.scss']
})
export class CircleComponent implements OnInit {
  @Input() backgroundColor: string;
  @Input() status: string;

  constructor() {}

  ngOnInit() {
    console.log(this.status);

  }
}
