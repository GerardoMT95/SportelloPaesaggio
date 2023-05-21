import { PrimeNgMenu } from '../../models/menu.model';
import { paths } from 'src/app/app-routing.module';

export const MAIN_MENU: PrimeNgMenu[] = [
  {
    label: 'aaaaa',
    items: [
      {
        label: 'MENU_ITEMS.NAV_INSTANCE',
        icon: 'fa fa-list',
        routerLink: paths.list()
      }
    ]
  },
  {
    label: 'bbb',
    items: [
      {
        label: 'test',
        icon: 'fa fa-list',
        routerLink: paths.list()
      }
    ]
  },
  {
    label: 'ccccc',
    items: [
      {
        label: 'testvvv',
        icon: 'fa fa-list',
        routerLink: paths.list()
      },
      {
        label: 'testaaaaaaaaaa',
        icon: 'fa fa-list',
        routerLink: paths.list()
      },
      {
        label: 'testcccccccccc',
        icon: 'fa fa-list',
        routerLink: paths.list()
      }
      
    ]
  }
];
