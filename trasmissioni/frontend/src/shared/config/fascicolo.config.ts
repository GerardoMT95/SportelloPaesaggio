import { RegistrationStatus } from 'src/shared/models/registration-status';
import { TableHeader } from "../models/table-header";
import { CONST } from "../constants";

export class FascicoloConfig
{

    static listTableHeaders(): any 
    { //pubblico
        //return FascicoloConfig.planListTableHeaders;
        return [
            /* { field: 'stato', header: 'operatore.table.stato', fieldName: 'stato', type: 'light' }, */
            { field: 'codice', header: 'operatore.table.codice', fieldName: 'codice', sortableField: true, type: 'text' },
            { field: 'note', header: 'operatore.table.descrizione', fieldName: 'oggettoIntervento', type: 'text' },
            { field: 'tipologia_intervento', header: 'operatore.table.tipologiaIntervento', fieldName: 'tipoProcedimento', type: 'text', valueOf: CONST.TipiProcedimento },
            { field: 'comuni', header: 'operatore.table.comune', fieldName: 'comuni', type: 'array' },
            { field: 'rup', header: 'operatore.table.responsabileProcedimento', fieldName: 'rup', type: 'text' }, //???
            { field: 'numero_provvedimento', header: 'operatore.table.numeroProvvedimento', fieldName: 'numeroProvvedimento', type: 'text' },
            { field: 'data_rilascio_autorizzazione', header: 'operatore.table.dataProvvedimento', fieldName: 'dataRilascioAutorizzazione', sortableField: true, type: 'date' },
            { field: 'esito', header: 'operatore.table.esitoProvvedimento', fieldName: 'esito', sortableField: true, type: 'text', valueOf: CONST.EsitoAutorizzazione },
            { field: 'id', header: '', width: 6, type: "button" },
            { field: 'id', header: '', width: 10 }
        ];
    }
    static privateListTableHeaders()
    {
        //return FascicoloConfig.colonneNonPublic.concat(...FascicoloConfig.planListTableHeaders);
        return [
            { field: 'codeStatoFascicolo', header: '', width: 3, fieldName: 'stato', type: 'light' },
            { field: 'codice', header: 'operatore.table.codice', sortableField: true, fieldName: 'codice', type: 'text' },
            { field: 'note', header: 'operatore.table.descrizione', fieldName: 'oggettoIntervento', type: 'text' },
            { field: 'tipologia_intervento', header: 'operatore.table.tipologiaIntervento', fieldName: 'tipoProcedimento', type: 'text', valueOf: CONST.TipiProcedimento },
            { field: 'comuni', header: 'operatore.table.comune', fieldName: 'comuni', type: 'array' },
            { field: 'rup', header: 'operatore.table.responsabileProcedimento', fieldName: 'rup', type: 'text' },
            { field: 'numero_provvedimento', header: 'operatore.table.numeroProvvedimento', fieldName: 'numeroProvvedimento', type: 'text' },
            { field: 'data_rilascio_autorizzazione', header: 'operatore.table.dataProvvedimento', sortableField: true, fieldName: 'dataRilascioAutorizzazione', type: 'date' },
            { field: 'esito', header: 'operatore.table.esitoProvvedimento', sortableField: true, fieldName: 'esito', type: 'text', valueOf: CONST.EsitoAutorizzazione },
            { field: 'stato_registrazione', header: 'operatore.table.statoRegistrazione', fieldName: 'statoRegistrazione', type: 'statoRegistrazione', valueOf: CONST.StatoRegistrazione },
            { field: 'esito_verifica', header: 'operatore.table.esitoVerifica', fieldName: 'esitoVerifica', type: 'text',valueOf: CONST.EsitiVerifica },
            { field: 'id', header: '', width: 8, type: 'button' }
        ];
    }

    static destinatariListTableHeaders(): TableHeader[]
    {
        return [
            { field: 'denominazione', header: 'fascicolo.mailCompose.ente', sortableField: false, width: 30 },
            { field: 'mailAddress', header: 'fascicolo.mailCompose.pecMail', sortableField: false, width: 30 },
            { field: 'to', header: '', sortableField: false, width: 6 } //mappate azioni....
        ];
    }

}

export enum FascicoloAzioni
{
    VIEW,
    EDIT,
    DELETE,
    DOWNLOAD_PROVVEDIMENTO
}

