import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-tooltip',
  template: `
    <i class="fa fa-info-circle info-icon" (mouseenter)="op1.toggle($event)" (mouseleave)="op1.toggle($event)"></i>
    <p-overlayPanel #op1 [appendTo]="'body'" [style]="{'max-width':'600px'}" >
      <p [innerHTML]="(message|translate) | safeHtml"></p>
    </p-overlayPanel>
  `,
  styles: [`
    .info-icon {
      margin-left: 10px;
    }
  `]
})
export class TooltipComponent implements OnInit {

  @Input() message: string;

  constructor() { }

  ngOnInit() {
  }

}
