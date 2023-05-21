import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-tooltip',
  template: `
    <i class="fa" (mouseenter)="op1.toggle($event)" (mouseleave)="op1.toggle($event)" 
      [ngClass]="{'fa-times-circle info-icon error': type === 'error', 'fa-info-circle info-icon': type === 'info'}"></i>
    <p-overlayPanel #op1 [appendTo]="'body'" [style]="{'max-width':'600px'}">
      <ng-container *ngIf="isHtml"> 
      <div [innerHTML]="message|safeHtml"></div>  
      </ng-container>
        <ng-container *ngIf="!isHtml">
          {{ message }}
        </ng-container>
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
  @Input() isHtml:boolean=false;

  constructor() { }

  ngOnInit() {
  }

}
