import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { TipoAllegatoRelazione } from 'src/app/shared/models/models';

export const documentTableHeaders = [
    {
      header: '',
      field: 'noStatus'
    },
    {
      header: 'Tipologia',
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

export const comunicationsTableHeaders = 
[
  {
    header: 'Mittente',
    field: 'mittenteEmail'
  },
  {
    header: 'Destinatari',
    field: 'destinatari'
  },
  {
    header: 'Oggetto',
    field: 'oggetto'
  },
  {
    header: 'Data creazione',
    field: 'dataInvio'
  },
  {
    header: 'Testo',
    field: 'testo'
  },
  {
    header: '',
    field: 'displayButton',
    width: 8
  }
  ];

export const grigliaAllegatiData = [
    {
      id: 1,
      descrizione: TipoAllegatoRelazione.relazioneTecnicaIlustrativa + '*',
      nome: '',
      data: null
    }
];

export const documentTypeOptions: SelectOption[] = [
  {
    value: 1,
    description: TipoAllegatoRelazione.relazioneTecnicaIlustrativa + '*'
  },
  {
    value: 2,
    description: TipoAllegatoRelazione.altro
  }
];
