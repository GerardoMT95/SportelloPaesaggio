import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-circle',
  templateUrl: './circle.component.html',
  styleUrls: ['./circle.component.scss']
})
export class CircleComponent implements OnInit {
  @Input() backgroundColor: string;
  /**
   * codifica dello stato della pratica per estrarre dal translate STATI."status"
   */
  @Input() status: string;
  @Input() labelInput: string;
  @Input() color: string;
  /**
   * override dello status, con il translate su tutta la stringa
   */
  @Input() statusInput: string;

  constructor() {}

  ngOnInit() {
  }
}
