import { SimpleFormControlData } from './../../../../../components/model/model';
import { SelectItem } from 'primeng/primeng';
export class DataConf
{
    private static optionsProtocolloIn: Array<SelectItem> = [
        {label: "IMAP"      , value: "IMAP"},
        {label: "IMAP IDLE" , value: "IMAP_IDLE"},
        {label: "IMAPS"     , value: "IMAPS"},
        {label: "IMAPS IDLE", value: "IMAPS_IDLE"},
        {label: "POP3"      , value: "POP3"}
    ];
    private static optionsProtocolloOut: Array<SelectItem> = [
        {label: "SMTP"      , value: "SMTP"},
        {label: "SMTPS"     , value: "SMTPS"}
    ]

    public static readonly formConf: {[key: string]: Array<SimpleFormControlData>} =
    {
        "casellapost": [
            { formControlName: "pecIndirizzo", type: "text", label: "Indirizzo email", required: true },
            { formControlName: "pecNome", type: "text", label: "nome visualizzato", required: true },
            { formControlName: "pecUsername", type: "text", label: "Username", required: true },
            { formControlName: "pecPassword", type: "password", label: "Password", required: true }
        ],
        "mailin": [
            { formControlName: "pecHostIn", type: "text", label: "Host", required: true },
            /* { formControlName: "pecProtocolloIn", type: "checkbox", label: "Protocollo", required: false }, */
            { formControlName: "pecPortaSslIn", type: "number", label: "Porta", required: true },
            { formControlName: "pecTipoProtocolloIn", type: "dropdown", label: "Protocollo", options: DataConf.optionsProtocolloIn, required: true },
            { formControlName: "pecSslIn", type: "checkbox", label: "Richiede Ssl", required: true },
            { formControlName: "pecStartTlsIn", type: "checkbox", label: "Richiede start tsl", required: false },
            { formControlName: "pecAutenticazioneIn", type: "checkbox", label: "Richiede autenticazione", required: false }
        ],
        "mailout": [
            { formControlName: "pecHostOut", type: "text", label: "Host", required: true },
            { formControlName: "pecPortaSslOut", type: "number", label: "Porta ssl", required: true },
            { formControlName: "pecPortaTlsOut", type: "number", label: "Porta tls", required: true },
            { formControlName: "pecTipoProtocolloOut", type: "dropdown", label: "Protocollo", options: DataConf.optionsProtocolloOut, required: true },
            { formControlName: "pecSslOut", type: "checkbox", label: "Richiede ssl", required: false },
            { formControlName: "pecTlsOut", type: "checkbox", label: "Richiede tls", required: false },
            { formControlName: "pecStartTlsOut", type: "checkbox", label: "Richiede start tls", required: false },
            { formControlName: "pecAutenticazioneOut", type: "checkbox", label: "Richiede autenticazione", required: false }
        ]

    }
}

export interface ConfigurazionePECBean
{
    pecIndirizzo: string;
	pecNome: string;
	pecUsername: string;
	pecPassword: string;
	pecHostIn: string;
	pecHostOut: string;
	pecProtocolloIn: boolean;
	pecProtocolloOut: boolean;
	pecTipoProtocolloIn: string;
	pecTipoProtocolloOut: string;
	pecSslIn: boolean;
	pecSslOut: boolean;
	pecTlsIn: boolean;
	pecTlsOut: boolean;
	pecPortaSslIn: number;
	pecPortaSslOut: number;
	pecPortaTlsIn: number;
	pecPortaTlsOut: number;
	pecStartTlsIn: boolean;
	pecStartTlsOut: boolean;
	pecAutenticazioneIn: boolean;
	pecAutenticazioneOut: boolean;
}