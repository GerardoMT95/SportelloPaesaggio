import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-tooltip',
  template: `
    <i class="fa" (mouseenter)="op1.toggle($event)" (mouseleave)="op1.toggle($event)" 
      [ngClass]="{'fa-times-circle info-icon error': type === 'error', 'fa-info-circle info-icon': type === 'info'}"></i>
    <p-overlayPanel #op1 [appendTo]="'body'" [style]="{'max-width':'600px'}">
      {{ message }}
    </p-overlayPanel>
  `,
  styles: [`
    .info-icon {
      margin-left: 10px;
    }
    .error
    {
      color: red;
    }
  `]
})
export class TooltipComponent implements OnInit {

  @Input() message: string;
  @Input() type: "info"|"error"="info";

  constructor() { }

  ngOnInit() {
  }

}
