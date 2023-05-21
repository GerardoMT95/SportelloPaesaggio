import { StatoEnum, StatoGroup } from '../models/models';

export const statoLegendConfig = 
{
  /* daAssegnare:
  {
    label: 'FASCIOLO_FIELDS.DA_ASSEGNARE',
    color: '#DC3545',
    timeLinePosition: '0'
  }, */
  inProtocollazione:
  {
    label: 'FASCIOLO_FIELDS.IN_PROTOCOLLAZIONE',
    color: '#eeb448',
    timeLinePosition: '0'
  },
  inPreistruttoria: 
  {
    label: 'FASCIOLO_FIELDS.IN_PRE-INVESTIGATION',
    color: '#757070',
    timeLinePosition: '0'
  },
  inLavorazione: 
  {
    label: 'FASCIOLO_FIELDS.UNDER_PROCESING',
    color: '#007BFF',
    timeLinePosition: '33'
  },
  daTrasmettere: 
  {
    label: 'FASCIOLO_FIELDS.TO_BE_TRANSMITTED',
    color: '#e8da0a',
    timeLinePosition: '66'
  },
  trasmessa: 
  {
    label: 'FASCIOLO_FIELDS.TRANSMITTED',
    color: '#08eb14',
    timeLinePosition: '100'
  },
  sospesa:
  {
    label: 'FASCIOLO_FIELDS.SOSPESA',
    color: '#bd1ada',
    timeLinePosition: '0'
  },
  archiviata:
  {
    label: 'FASCIOLO_FIELDS.ARCHIVIATA',
    color: '#000',
    timeLinePosition: '0'
  },
};

export class ProcessingStatus
{
  static getStatus(status: StatoEnum): StatoGroup 
  {
    switch (status) 
    {
      /* case StatoEnum.NonAssegnata:
        return {
          label: statoLegendConfig.daAssegnare.label,
          color: statoLegendConfig.daAssegnare.color,
          timeLinePosition: statoLegendConfig.daAssegnare.timeLinePosition
        }; */
      //c'è scritto da eliminare
      /* case StatoEnum.IstanzaPresentata:
        return {
          label: statoLegendConfig.daAssegnare.label,
          color: statoLegendConfig.daAssegnare.color,
          timeLinePosition: statoLegendConfig.daAssegnare.timeLinePosition
        }; */
      //case StatoEnum.inProtocollazione:
      case StatoEnum.InAttesaDiProtocollazione:
        return {
          label: statoLegendConfig.inProtocollazione.label,
          color: statoLegendConfig.inProtocollazione.color,
          timeLinePosition: statoLegendConfig.inProtocollazione.timeLinePosition
        };
      case StatoEnum.PresaInCarica: //sarebbe IN_PREISTRUTTORIA
        return {
          label: statoLegendConfig.inPreistruttoria.label,
          color: statoLegendConfig.inPreistruttoria.color,
          timeLinePosition: statoLegendConfig.inPreistruttoria.timeLinePosition
        };
      case StatoEnum.InLavorazione:
        return {
          label: statoLegendConfig.inLavorazione.label,
          color: statoLegendConfig.inLavorazione.color,
          timeLinePosition: statoLegendConfig.inLavorazione.timeLinePosition
        };
      /* case StatoEnum.RelazioneDaTrasmettere:
        return {
          label: statoLegendConfig.inLavorazione.label,
          color: statoLegendConfig.inLavorazione.color,
          timeLinePosition: statoLegendConfig.inLavorazione.timeLinePosition
        };
      case StatoEnum.InAttesaDiParereSoprintedenza:
        return {
          label: statoLegendConfig.inLavorazione.label,
          color: statoLegendConfig.inLavorazione.color,
          timeLinePosition: statoLegendConfig.inLavorazione.timeLinePosition
        };
      case StatoEnum.ParereSoprintendenzaTrasmesso:
        return {
          label: statoLegendConfig.inLavorazione.label,
          color: statoLegendConfig.inLavorazione.color,
          timeLinePosition: statoLegendConfig.inLavorazione.timeLinePosition
        }; */
      case StatoEnum.InTrasmissione:
        return {
          label: statoLegendConfig.daTrasmettere.label,
          color: statoLegendConfig.daTrasmettere.color,
          timeLinePosition: statoLegendConfig.daTrasmettere.timeLinePosition
        };
      case StatoEnum.Trasmessa:
        return {
          label: statoLegendConfig.trasmessa.label,
          color: statoLegendConfig.trasmessa.color,
          timeLinePosition: statoLegendConfig.trasmessa.timeLinePosition
        };
      case StatoEnum.Sospesa:
        return {
          label: statoLegendConfig.sospesa.label,
          color: statoLegendConfig.sospesa.color,
          timeLinePosition: statoLegendConfig.sospesa.timeLinePosition
        };
      case StatoEnum.Archiviata:
        return {
          label: statoLegendConfig.archiviata.label,
          color: statoLegendConfig.archiviata.color,
          timeLinePosition: statoLegendConfig.archiviata.timeLinePosition
        };
        default:
          alert("Stato non ammesso :"+status);
    }
  }
}
