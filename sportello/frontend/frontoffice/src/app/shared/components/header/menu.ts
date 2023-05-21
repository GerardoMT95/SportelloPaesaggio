import { PrimeNgMenu, PrimeNgMenuItems } from 'src/app/core/models/menu.model';
//import { Menu } from "../model/model";

/**
 * qui andrebbero gli item di menu' pubblici
 */
export const MAIN_MENU: PrimeNgMenu =
{
  label:"",
  items:  [
    {
    label: "MENU_ITEMS.NAV_INSTANCE",
    icon: "fa fa-list",
    routerLink: `/gestione-istanze`,
  },
  {
    label: "MENU_ITEMS.VERIFICA_HASH",
    icon: "fa fa-barcode",
    routerLink: `/verifica-hash`,
  },
  {
    label: "MENU_ITEMS.SUBENTRO",
    icon: "fa fa-user",
    routerLink: `/cambio-ownership/lavorazione`,
  },
  {
    label: "MENU_ITEMS.MANUALE",
    icon: "fa fa-book",
    routerLink: `/download-manuale`,
  }
]
};
