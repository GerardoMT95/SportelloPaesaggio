import { CONST } from "src/shared/constants";
import { PrimeNgMenu, PrimeNgMenuItems } from "../model/model";
import { GroupType, GroupRole } from './../model/enum';


export const ADMIN_MENU_ITEM: PrimeNgMenuItems[] = 
[
  {
    label: "headermenu.attivaz",
    routerLink: "/admin/configura",
    icon: "folder",
    allowedRoles: ['*']
  },
  {
    label: "headermenu.annullIstanza",
    routerLink: "/admin/annulla-fascicolo",
    icon: "folder",
    allowedRoles: ['*']
  },
  {
    label: "headermenu.modIstanza",
    routerLink: "/admin/attiva-modifica",
    icon: "folder",
    allowedRoles: ['*']
  },
  {
    label: "headermenu.configuraTipiProcedimento",
    routerLink: "/admin/validita-tipi-procedimento",
    icon: "folder",
    allowedRoles: ['*']
  },
  {
    label: "headermenu.templCom",
    routerLink: "/admin/templates",
    icon: "folder",
    allowedRoles: ['*']
  },
  {
    label: "headermenu.rubricaEnti",
    routerLink: "/admin/rubrica",
    icon: "folder",
    allowedRoles: ['*']
  },
  {
    label: "headermenu.templateDocumentazioni",
    routerLink: "/admin/template-documentazioni",
    icon: "folder",
    allowedRoles: ['*']
  },
  {
    label: "headermenu.configuraProtocollo",
    routerLink: "/admin/configura-protocollo",
    icon: "folder",
    allowedRoles: ['*']
  },
  {
    label: "headermenu.confpec",
    routerLink: "/admin/gestione-PEC",
    icon: "folder",
    allowedRoles: ['*']
  },
  {
    label: 'headermenu.organizzazioni',
    routerLink: '/admin/gestione-organizzazioni',
    icon: 'folder',
    allowedRoles: ['*']
  },
  {
    label: 'headermenu.sbapemail',
    routerLink: '/admin/gestione-email-organizzazioni/SOPRINTENDENZA',
    icon: 'folder',
    allowedRoles: ['*']
  },
  {
    label: 'headermenu.provemail',
    routerLink: '/admin/gestione-email-organizzazioni/PROVINCIA',
    icon: 'folder',
    allowedRoles: ['*']
  },
  {
    label: 'headermenu.comemail',
    routerLink: '/admin/gestione-email-organizzazioni/ENTE_COMUNALE',
    icon: 'folder',
    allowedRoles: ['*']
  },
  {
    label: 'headermenu.diogeneConfig',
    routerLink: '/admin/diogene-config',
    icon: 'folder',
    allowedRoles: ['*']
  },
  {
    label: 'headermenu.integrazioneSportelloAmbiente',
    routerLink: '/admin/integrazione-sportello-config',
    icon: 'folder',
    allowedRoles: ['*']
  }

];
  

export const MAIN_MENU: PrimeNgMenu[] = 
[
  {
    label: "",
    items: [
      {
        label: "headermenu.gestioneProvvedimenti",
        routerLink: "/private/fascicolo",
        //icon: "fa fa-folder",
        icon: "folder",
        //allowedRoles: CONST.roles,
        allowedRoles: ['*'],
        grant: [
          { group: GroupType.ADMIN },
          { group: GroupType.ETI },
          { group: GroupType.REG },
          { group: GroupType.SBAP },
          /* { group: GroupType.EPA }, */
          { group: GroupType.ED },
        ]
      },
      {
        label: "headermenu.consultazionePubblicaProvvedimenti",
        routerLink: "/public/fascicolo",
        //icon: "fa fa-list",
        icon: "list",
        allowedRoles: ['*'],
      },
      {
        label: "headermenu.verificaImpronta",
        routerLink: "/private/verifica-hash",
        //icon: "fa fa-folder",
        icon: "barcode",
        //allowedRoles: CONST.roles,
        allowedRoles: ['*'],
        grant: [
          { group: GroupType.ADMIN },
          { group: GroupType.ETI },
          { group: GroupType.REG },
          { group: GroupType.SBAP },
          /* { group: GroupType.EPA }, */
          { group: GroupType.ED },
        ]
      }, 
      /*{
        label: "headermenu.cartografiaProvvedimenti",
        routerLink: "/public/fascicolo-cartografia",
        icon: "map",
        allowedRoles: ['*']
      },*/
      {
        label: "headermenu.manuale",
        routerLink: "/private/manuale",
        //icon: "fa fa-book",
        icon: "book",
        allowedRoles: ['*']
      },
      {
        label: "headermenu.amministrazione",
        routerLink: "",
        //icon: "fa fa-arrow-circle-down",
        icon: "arrow-circle-down",
        allowedRoles: ['*'],
        subMenu:ADMIN_MENU_ITEM,
        //show: false,
        grant: [{ group: GroupType.ADMIN }]
      },
      {
        label: "headermenu.richiestaAbilitazione",
        routerLink: "/richiesta-abilitazione",
        //icon: "fa fa-user",
        icon: "user",
        allowedRoles: ['*'],
      },
      {
        label: "headermenu.assegnaFascicoloConfig",
        routerLink: "/assegna-fascicolo-config",
        //icon: "fa fa-file",
        icon: "file",
        allowedRoles: ['*'], 
        grant: [
          /*{ 
            group: GroupType.ED,
            roles: [GroupRole.A]
          },*/
          {
            group: GroupType.SBAP,
            roles: [GroupRole.A]
          },
          {
            group: GroupType.ETI,
            roles: [GroupRole.A]
          },
          /*{
            group: GroupType.REG,
            roles: [GroupRole.A]
          },*/
          /* {
            group: GroupType.EPA,
            roles: [GroupRole.A]
          } */
        ]
      }
      ,
      {
        label: "headermenu.assegnaFascicolo",
        routerLink: "/assegna-fascicolo",
        //icon: "fa fa-file",
        icon: "file",
        allowedRoles: ['*'],
        grant: [
          /* { 
            group: GroupType.ED,
            roles: [GroupRole.A, GroupRole.D]
          }, */
          {
            group: GroupType.SBAP,
            roles: [GroupRole.A, GroupRole.D]
          },
          {
            group: GroupType.ETI,
            roles: [GroupRole.A, GroupRole.D]
          },
          /* {
            group: GroupType.REG,
            roles: [GroupRole.A, GroupRole.D]
          }, */
          /* {
            group: GroupType.EPA,
            roles: [GroupRole.A, GroupRole.D]
          } */
        ]
      }
    ]
  }
];


