import { paths } from 'src/app/app-routing.module';
import { PrimeNgMenu } from '../../models/menu.model';
import { GroupRole, GroupType } from '../../../shared/models/models';

export const MAIN_MENU: PrimeNgMenu =
{
  label: "",
  items: [
    /* {
      label: "MENU_ITEMS.NAV_INSTANCE",
      icon: "fa fa-list",
      routerLink: paths.listManagement(),
      grant: [{ group: GroupType.Richiedente }]
    }, */
    {
      label: 'MENU_ITEMS.NAV_INSTANCE_MANAGEMENT',
      icon: 'fa fa-list',
      grant:
        [
          { group: GroupType.Richiedente },
          { group: GroupType.Regione },
          { group: GroupType.Soprintendenza },
          { group: GroupType.EnteDelegato },
          { group: GroupType.EnteTerritoriale },
          { group: GroupType.CommissioneLocale },
          { group: GroupType.Amministratore }
        ],
      routerLink: paths.listManagement()
    },
    {
      label: 'MENU_ITEMS.VERIFICA_HASH',
      icon: 'fa fa-barcode',
      grant:'ALL',
      routerLink: paths.verificaHash()
    },
    {
      label: 'MENU_ITEMS.ASSEGNA_FASCICOLI',
      icon: 'fa fa-user',
      grant:
      [
        /* {
          group: GroupType.Regione,
          roles: [GroupRole.A, GroupRole.D]
        }, */
        {
          group: GroupType.Soprintendenza,
          roles: [GroupRole.A, GroupRole.D]
        },
        {
          group: GroupType.EnteDelegato,
          roles: [GroupRole.A, GroupRole.D]
        },
        {
          group: GroupType.EnteTerritoriale,
          roles: [GroupRole.A, GroupRole.D]
        }
      ],
      routerLink: paths.assign()
    },
    {
      label: 'MENU_ITEMS.ADMINISTRATIVE_FUNCTIONS',
      icon: 'fa fa-arrow-circle-down',
      grant:
      [
        {
          group: GroupType.Regione,
          roles: [GroupRole.A]
        },
        {
          group: GroupType.Soprintendenza,
          roles: [GroupRole.A]
        },
        {
          group: GroupType.EnteDelegato,
          roles: [GroupRole.A]
        },
        {
          group: GroupType.EnteTerritoriale,
          roles: [GroupRole.A]
        },
      ],
      subMenu: [
        {
          label: 'MENU_ITEMS.SERVIZI_INTEGRAZIONE',
          icon: 'fa fa-folder-o',
          routerLink: paths.serviziIntegrazione(),
          grant: [
            {
              group: GroupType.Regione,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.Soprintendenza,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.EnteDelegato,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.EnteTerritoriale,
              roles: [GroupRole.A]
            },
          ]
        },
        {
          label: 'MENU_ITEMS.CONF_ASF',
          icon: 'fa fa-folder-o',
          routerLink: paths.configuraAssegnamento(),
          grant: [
            /* {
              group: GroupType.Regione,
              roles: [GroupRole.A]
            }, */
            {
              group: GroupType.Soprintendenza,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.EnteDelegato,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.EnteTerritoriale,
              roles: [GroupRole.A]
            },
          ]
        },
        {
          label: 'MENU_ITEMS.TEMPLATE_COMUNICAZIONI',
          icon: 'fa fa-folder-o',
          routerLink: paths.comunicationsTemplateList(),
          grant: [
            {
              group: GroupType.EnteDelegato,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.Regione,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.EnteTerritoriale,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.Soprintendenza,
              roles: [GroupRole.A]
            },
          ]
        },
        {
          label: 'MENU_ITEMS.TEMPLATE_DOC',
          icon: 'fa fa-folder-o',
          routerLink: paths.modificaTemplateDoc(),
          grant: [
            {
              group: GroupType.EnteDelegato,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.Regione,
              roles: [GroupRole.A]
            }
          ]
        },
        {
          label: 'MENU_ITEMS.CENS_RUP',
          icon: 'fa fa-folder-o',
          routerLink: paths.configuraRup(),
          grant: [
            {
              group: GroupType.EnteDelegato,
              roles: [GroupRole.A]
            },
          ]
        },
        {
          label: 'MENU_ITEMS.configProc',
          icon: 'fa fa-cog',
          routerLink: paths.configurazioneProcedimentiEnte(),
          grant: [
            {
              group: GroupType.EnteDelegato,
              roles: [GroupRole.A]
            },
            {
              group: GroupType.Regione,
              roles: [GroupRole.A]
            }
          ]
        }
      ]
    },
    {
      label: 'MENU_ITEMS.ADMINISTRATIVE_FUNCTIONS',
      icon: 'fa fa-arrow-circle-down',
      grant: [{ group: GroupType.Amministratore }],
      subMenu:
        [
          {
            label: 'MENU_ITEMS.ENTITY_ADDRESSES',
            icon: 'fa fa-folder-o',
            routerLink: paths.modificaIndirizziEnte(),
          },
          {
            label: 'MENU_ITEMS.NUOVA-COMMISSIONE',
            icon: 'fa fa-folder-o',
            routerLink: paths.nuovaCommissioneLocale(),
          },
          {
            label: 'MENU_ITEMS.CONFIGURA-SEZIONI-CATASTALI',
            icon: 'fa fa-folder-o',
            routerLink: paths.configuraLabelSezioniCatastali()
          },
          {
            label: 'MENU_ITEMS.SETUP-MESSAGGI-SPORTELLO',
            icon: 'fa fa-folder-o',
            routerLink: paths.setupMessaggiSsortello()
          },
          {
            label: 'MENU_ITEMS.CONFIGURAZIONE-DIOGENE',
            icon: 'fa fa-folder-o',
            routerLink: paths.configurazioneDiogene()
          },
          {
            label: 'MENU_ITEMS.configProc',
            icon: 'fa fa-cog',
            routerLink: paths.configurazioneProcedimenti()
          }
        ]
    },
    {
      label: 'MENU_ITEMS.COM_LOC',
      icon: 'fa fa-folder-o',
      routerLink: paths.commissioneLocale(),
      grant:
      [
        { group: GroupType.EnteDelegato },
        { group: GroupType.CommissioneLocale }
      ],
    },
    {
      label: 'MENU_ITEMS.REPORTS',
      icon: 'fa fa-bar-chart',
      routerLink: paths.reports(),
      grant:
      [
        { group: GroupType.EnteDelegato 
          ,roles: [GroupRole.D]},
        { group: GroupType.Regione 
            ,roles: [GroupRole.D]},
        
      ],
    },
    {
      label: 'MENU_ITEMS.RICHIESTA_ABILITAZIONE',
      icon: 'fa fa-user',
      grant:'ALL',
      routerLink: paths.richiestaAbilitazione()
    },
    {
      label: 'MENU_ITEMS.MANUALE',
      icon: 'fa fa-book',
      grant:'ALL',
      routerLink: paths.manuale()
    },
    /* {
      label: 'MENU_ITEMS.PUBLIC',
      icon: 'fa fa-list',
      routerLink: paths.publicList(),
      grant: '*'
    } */
  ]
};