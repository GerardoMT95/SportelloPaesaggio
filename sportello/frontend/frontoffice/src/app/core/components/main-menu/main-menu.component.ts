import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MAIN_MENU } from './primeNgMenu';
import { TranslateService } from '@ngx-translate/core';
import { PrimeNgMenu } from '../../models/menu.model';

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.scss']
})
export class MainMenuComponent implements OnInit {

  @Input() collapsed;
  @Output() collapsedChanged: EventEmitter<boolean> = new EventEmitter<boolean>();
  primeNgMenu: PrimeNgMenu[] = [];
  menuItems: PrimeNgMenu[];

  constructor(private translateService: TranslateService) { }

  ngOnInit() {
    this.primeNgMenu = MAIN_MENU.map(item => {
      item.items.map(item => this.translateService.get(item.label)
      .subscribe((response: string) => { item.label = response}));
      return item;
    } );
    this.menuItems = MAIN_MENU;
  }

  hideMenu() {
    this.collapsedChanged.emit(true);
  }

}
