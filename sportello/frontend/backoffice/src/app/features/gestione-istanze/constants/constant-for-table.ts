import { TableConfig } from "src/app/core/models/header.model";

export class IstanzeConst
{
    /*public static readonly TIPO_PROC_CONST =
    [
        {
            value: "AUT_PAES_ORD",
            description: "AUTORIZZAZIONE PAESAGGISTICA art. 146, D. Lgs. 42/2004 – art. 90, NTA PPTR (ORDINARIA)"
        },
        {
            value: "AUT_PAES_SEMPL_DPR_31_2017",
            description: "AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 31/2017– art. 90, NTA PPTR PER INTERVENTI E OPERE DI LIEVE ENTITA’ SOGGETTI AL PROCEDIMENTO AUTORIZZATORIO SEMPLIFICATO A NORMA DELL’ARTICOLO 146 C.9 DEL D.LGS 42/2004"
        },
        {
            value: "ACCERT_COMPAT_PAES_DLGS_42_2004",
            description: "ACCERTAMENTO DI COMPATIBILITA’ PAESAGGISTICA Art. 167 e 181, D. Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)"
        },
        {
            value: "ACCERT_COMPAT_PAES_DPR_139_2010",
            description: "ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R. 139/2010] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL'ART. 38 C. 3.1 NTA PPTR)."
        }
    ];*/

    public static readonly FASCICOLO_TABLE_HEADERS:TableConfig[] =
    [
        {
            header: '',
            field: 'stato',
            width: 3
        },
        {
            header: 'FASCIOLO_FIELDS.FILE_CODE',
            field: 'codicePraticaAppptr',
            width: 8,
            orderable:true
        },
        {
            header: 'FASCIOLO_FIELDS.COMMON',
            width: 10,
            field: 'comuniIntervento'
        },
        {
            header: 'FASCIOLO_FIELDS.OBJECT',
            width: 18,
            field: 'oggetto',
            orderable:true
        },
        {
            header: 'FASCIOLO_FIELDS.PROCEDURE_TYPE',
            field: 'tipoProcedimento',
            orderable:true
        },
        {
            header: 'FASCIOLO_FIELDS.STATE',
            width: 10,
            field: 'stato',
            orderable:true
        },
        {
            header: 'FASCIOLO_FIELDS.STATO_ATTIVITA',
            field: 'statoAttivita',
        },
        {
            header: 'FASCIOLO_FIELDS.USER',
            field: 'user',
            width: 10,
        },
        {
            header: 'FASCIOLO_FIELDS.ENTE_DELEGATO',
            field: 'descrEnteDelegato',
            width: 10,
        },
        {
            header: 'fascicolo.funzionario',
            field: 'denominazioneAssegnatarioOrganizzazione',
        },
        //colonna azioni, viene mappata con nome field a displayButton
        {
            header: '',
            field: 'displayButton',
            width: 8
        }
    ];
}
    