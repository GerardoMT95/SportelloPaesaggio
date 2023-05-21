import { TableConfig } from './../../../core/models/header.model';
import { SelectItem } from 'primeng/primeng';
import { HierarchicalOption, Allegato } from './../../../shared/models/models';
import { Validators } from '@angular/forms';
import { CONST } from 'src/app/shared/constants';
export class IstanzaConst
{
    private static _art136: HierarchicalOption =
    {
        dependency: null,
        name: 'art136',
        options:[
            {
                key: 1,
                value: '136',
                label: 'dell\'art. 136, del d.lgs. n. 42/2004',
                hasText: false,
                orderIndex: 1,
                tooltip: 'DICHIARAZIONI_TAB.TOOLTIP1'
            }
        ],
        type: 'checkbox',
        children: 
        {
            dependency: 1,
            name: 'art136Child',
            options: [
                {
                    key: null,
                    value: 'A',
                    label: 'c. 1, lett. a)',
                    hasText: false,
                    orderIndex: 2
                },
                {
                    key: null,
                    value: 'B',
                    label: 'c. 1, lett. b)',
                    hasText: false,
                    orderIndex: 3
                },
                {
                    key: null,
                    value: 'C',
                    label: 'c. 1, lett. c)',
                    hasText: false,
                    orderIndex: 4
                },
                {
                    key: null,
                    value: 'D',
                    label: 'c. 1, lett. d)',
                    hasText: false,
                    orderIndex: 5
                }
            ],
            type: 'checkbox'
        }
    };

    private static _art142: HierarchicalOption =
    {
        dependency: null,
        name: 'art142',
        options: [
            {
                key: 2,
                value: '142',
                label: 'dell\'art. 142, del d.lgs. n. 42/2004',
                hasText: false,
                orderIndex: 1
            }
        ],
        type: 'checkbox'
    };

    private static _art134: SelectItem =
    {

        label: 'dell\'art. 134, c.1,lett c) del d.lgs. n. 42/2004',
        value: '134'
    };

    private static _disclaimer: any = [
        { 
            value: 6,
            label: "di astenersi dall'avviare i lavori fino a quando non ha ottenuto la prescritta autorizzazione paesaggistica" 
        },
        { 
            value: 7, 
            label: "di essere informato che l'autorizzazione paesaggistica non è atto che legittima l'esecuzione dei lavori" 
        }, 
        { 
            value: 8,
            label: "di essere a conoscenza che l'autorizzazione paesaggistica ha valore esclusivamente per la valutazione ai fini della tutela paesaggistica e non sulla conformità delgli strumenti urbanistici adottati o approvati, ai regolamenti edilizi e di settore, per i quali il progetto deve rispettare le norme di riferimento vigenti" 
        }, 
        { 
            value: 9, 
            label: "di aver letto l'informativa sui trattamento dei dati personali posta al termine del presente modulo" 
        }
    ];

    private static _shapeFileAllegatoInit: Allegato[] =
    [
        {
            descrizione: 'AREA INTERVENTO SHAPE FILE',
            nome: '',
            data: null,
            id: null
        }
    ];

    private static _localizationTableHeaders1: TableConfig[] =
    [
      {
        header: "LOCALIZZAZIONE.COMUNE",
        field: "id",
        type: "hidden"
      },
      {
        header: "LOCALIZZAZIONE.LIVELLO",
        field: "livello",
        type: "select",
        optionSelect: [{ value: 'PARTICELLE', description: 'PARTICELLE' }, { value: 'ACQUE', description: 'ACQUE' }, { value: 'STRADE', description: 'STRADE' }],
        validators:[Validators.required]
      },
      {
        header: "LOCALIZZAZIONE.SEZ",
        field: "sezione",
        type: "text-field"
      },
      {
        header: "LOCALIZZAZIONE.FOG",
        field: "foglio",
        type: "text-field",
        validators:[Validators.required,Validators.pattern(CONST.PATTERN_NUMERI)]
      },
      {
        header: "LOCALIZZAZIONE.PART",
        field: "particella",
        type: "text-field",
        validators:[Validators.pattern(CONST.PATTERN_NUMERI)]
      },
      {
        header: "LOCALIZZAZIONE.SUB",
        field: "sub",
        type: "text-field",
        validators:[Validators.pattern(CONST.PATTERN_NUMERI)]
      },
      {
        header: "LOCALIZZAZIONE.NOTE",
        field: "note",
        type: "text-field",
      },
      {
        header: "",
        field: "oid",
        type: "hidden",
      },
      {
        header: "",
        field: "codCat",
        type: "hidden",
      }
    ];

    private static _localizationTableHeaders2: TableConfig[] =
    [
      {
        header: '',
        field: 'noStatus'
      },
      {
        header: 'Descrizione',
        field: 'descrizione'
      },
      {
        header: 'Nome file',
        field: 'nome'
      },
      {
        header: 'Data',
        field: 'data'
      },
      {
        header: 'Impronta hash',
        field: 'checksum'
      }
    ];

    private static _documentoReferenteCOnfigTable: TableConfig[] =
    [
        {
          header: '',
          field: 'noStatus'
        },
        {
          header: 'Nome file',
          field: 'nome'
        },
        {
          header: 'Descrizione',
          field: 'descrizione'
        },
        {
          header: 'Data caricamento',
          field: 'data'
        },
        {
          header: 'Impronta hash',
          field: 'checksum'
        }
    ]

    static get art136(): HierarchicalOption { return this._art136; }
    static get art142(): HierarchicalOption { return this._art142; }
    static get art134(): SelectItem { return this._art134; }
    static get disclaimer(): any { return this._disclaimer; }
    static get shapeFileAllegatoInit(): Allegato[] { return this._shapeFileAllegatoInit; }
    static get firstLocalizationTableHeaders(): TableConfig[] { return this._localizationTableHeaders1; }
    static get seconLocalizationTableHeaders2(): TableConfig[] { return this._localizationTableHeaders2; }
  static get documentoReferenteCOnfigTable(): TableConfig[] { return this._documentoReferenteCOnfigTable; }
}