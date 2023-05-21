import { TableConfig } from 'src/app/core/models/header.model';
import { DocumentType } from './../../shared/models/allegati.model';

export const documentTableHeaders = [
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
      header: 'Data caricamento',
      field: 'data'
    }
  ];

export const documentTypes: Array<DocumentType> = [
  {
    label: 'Provvedimento finale (pubblico)',
    multiple: false,
    required: true,
    type: 951,
    accept: ['application/pdf']
  },
  {
    label: 'Provvedimento finale (privato)',
    multiple: false,
    required: false,
    type: 950,
    accept: ['application/pdf']
  }
]

export const FILE_TABLE: TableConfig[] =
  [
    {
      field: "type",
      header: "Tipologia",
      type: "type"
    },
    {
      field: "nome",
      header: "Nome file",
      type: "text"
    },
    {
      field: "data",
      header: "Data caricamento",
      type: "date"
    },
    {
      field: "checksum",
      header: "Impronta hash",
      type: "text"
    }
  ];