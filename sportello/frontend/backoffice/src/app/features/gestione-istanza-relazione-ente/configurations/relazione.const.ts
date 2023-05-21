import { TemplateComunicazione } from './../../gestione-istanza-comunicazioni/model/corrispondenza.models';
import { DocumentType } from './../../../shared/models/allegati.model';
import { TableConfig } from 'src/app/core/models/header.model';
export class RelazioneEnteConst
{
    private static _types: DocumentType[] =
    [
        {
            label: "Relazione tecnica illustrativa",
            type: "REL_TEC_ILL",
            multiple: false,
            required: true,
        },
        {
            label: "Altro",
            type: "OTHER",
            multiple: false,
            required: false
        }
    ];
    //da spostare in un json per i template... parte templating da fare con funzionalità admin
    /* private static _templates: TemplateComunicazione[] =
    [
        {
            label: "Trasmissione Relazione Tecnica - con avvio",
            descrizione: "",
            value:"TRASM_AVVIO",
            template: 
            {
                destinatari: [],
                oggetto: "{tipo_procedimento} - TRASMISSIONE RELAZIONE TECNICO ILLUSTRATIVA e proposta di provvedimento Comune di {comune} - Ditta: {ditta} - [TIPO PROCEDIMENTO] -  AVVIO DEL PROCEDIMENTO - trasmissione relazione tecnica illustrativa e proposta di provvedimento della domanda (L.n. 241/90, art. 146 c. 7 del D.Lgs. n. 42/2004 e art. 90 co. 4 NTA PPTR). ",
                testo: "Con la presente si fa riferimento alla richiesta di rilascio di autorizzazione paesaggistica relativa al progetto per {oggetto}, acquisita al protocollo di questo servizio con n. AOO_145- .. del {data_protocollo} Pertanto si trasmette la relazione tecnica illustrativa unitamente alla proposta di accoglimento della domanda di competenza dello scrivente Servizio, alla Soprintendenza per i provvedimenti di competenza, ai sensi del co. 7 art. 146 del D.Lgs. 42/04 e, contestualmente, si comunica, il solo avvio del procedimento istruttoria al {comune}, indicando nella persona del {rup} (xxxxxxxxx@regione.puglia.it) il Responsabile del Procedimento.  Il Responsabile del Procedimento  (arch./ing./ geom. .... )  Il dirigente \"ad interim\" del Servizio  Attuazione Pianificazione Paesaggistica  ()  La firma autografa del dirigente del Servizio e del Responsabile del Procedimento è sostituita a mezzo stampa ai sensi dell' art. 3 comma 2 del D.L. 39/1993 e ss.mm.ii.",
                riservata:true,
            }
        },
        {
            label: "Trasmissione Relazione Tecnica - senza avvio",
            descrizione: "",
            value:"TRASM_NO_AVVIO",
            template: 
            {
                destinatari: [],
                oggetto: "Ricezione- {tipo_procedimento} - {codice_fascicolo}",
                testo: "Con la presente si fa riferimento alla richiesta di rilascio di autorizzazione paesaggistica relativa al progetto per {oggetto}, acquisita al protocollo di questo servizio con n. AOO_145- ...... del {data_protocollo} Pertanto si trasmette la relazione tecnica illustrativa unitamente alla proposta di accoglimento della domanda di competenza dello scrivente Servizio, alla Soprintendenza per i provvedimenti di competenza, ai sensi del co. 7 art. 146 del D.Lgs. 42/04. Il Responsabile del Procedimento  (arch./ing./ geom. .......)  Il dirigente ''ad interim''del Servizio Attuazione Pianificazione Paesaggistica () La firma autografa del dirigente del Servizio e del Responsabile del Procedimento è sostituita a mezzo stampa ai sensi  dell' art. 3 comma 2 del D.L. 39/1993 e ss.mm.ii. ",
                riservata:true,
            }
        }
    ]; */

    private static _comunicazioniTableHeaders: TableConfig[] = 
    [
        {
            header: 'Mittente',
            field: 'mittente'
        },
        {
            header: 'Destinatari',
            field: 'aggiungiDestinario'
        },
        {
            header: 'Riserva',
            field: 'riserva'
        },
        {
            header: 'Oggetto',
            field: 'oggetto'
        },
        {
            header: 'Data creazione',
            field: 'data'
        },
        {
            header: 'Testo',
            field: 'testoTemplate'
        },
        {
            header: '',
            field: 'displayButton',
            width: 8
        }
    ];

    public static readonly relazione_table: TableConfig[] =
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

    static get documentTypes(): DocumentType[] { return this._types; }
    /* static get templates(): TemplateComunicazione[] { return this._templates; } */
    static get comunicazioneHeaders(): TableConfig[] { return this._comunicazioniTableHeaders; }
}