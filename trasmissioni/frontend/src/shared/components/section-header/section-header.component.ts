import { Component, Input } from "@angular/core";

@Component({
  selector: "app-section-header",
  template: `
  <div class="section-header" [style.margin-top]="marginTop + 'px'" [style.margin-bottom]="marginBottom + 'px'">
    <div class="section-header-line"></div>
    <div class="section-header-tab">{{ title }}</div>
  </div>
  `,
  styles: [
    `
      .section-header {
        height: 28px;
      }
      .section-header-line {
        height: 2px;
        width: 100%;
        background-color: rgb(223, 223, 223);
        position: relative;
        top: 50%;
        z-index: 1;
      }
      .section-header-tab {
        width: auto;
        padding: 3px 15px;
        display: inline-block;
        background-color: rgb(223, 223, 223);
        font-size: 15px;
        margin-left: 30px;
        text-align: center;
        position: relative;
        z-index: 2;
      }
    `
  ]
})
export class SectionHeaderComponent {

  @Input() title: string;
  @Input() marginTop = 20;
  @Input() marginBottom = 20;

  constructor() {
  }

}
