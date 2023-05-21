import { SelectItem } from 'primeng/primeng';
import { TableConfig } from 'src/app/core/models/header.model';
import { DocumentType } from './../../shared/models/allegati.model';

export const documentTableHeadersParere: TableConfig[] = 
[
    {
      header: 'Tipologia',
      field: 'type',
      type: 'type'
    },
    {
      header: 'Nome file',
      field: 'nome',
      type: 'name',
    },
    {
      header: 'Data caricamento',
      field: 'data',
      type: 'date'
    },
    {
      header: 'Impronta hash',
      field: 'checksum',
      type: 'text'
    }
];


export const comunicationsTableHeaders = [
    {
      header: 'Mittente',
      field: 'mittente'
    },
    {
      header: 'Destinatari',
      field: 'aggiungiDestinario'
    },
    {
      header: 'oggetto',
      field: 'oggetto'
    },
    {
      header: 'Data creazione',
      field: 'data'
    }
  ];

export const grigliaAllegatiData: DocumentType[] = 
[
  {
    label: 'Parere MIBAC (pubblico)',
    multiple: false,
    required: true,
    type: 904,
  },
  {
    label: 'Parere MIBAC (privato)',
    multiple: false,
    required: false,
    type: 903,
  }
];

export const EsitoParereOption: any[] =
[
    { description: 'Non autorizzato'             , value: 'NON_AUTORIZZATO' },
    { description: 'Autorizzato'                 , value: 'AUTORIZZATO'     },
    { description: 'Autorizzato con prescrizione', value: 'AUT_CON_PRESCRIZ'}
]
