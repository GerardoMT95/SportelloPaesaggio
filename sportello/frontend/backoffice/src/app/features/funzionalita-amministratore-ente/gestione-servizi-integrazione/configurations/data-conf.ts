import { CONST } from 'src/app/shared/constants';
import { Validator, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { SimpleFormControlData } from './../models/ente-admin.models';
import { customEmailValidator } from 'src/app/shared/validators/customValidator';
export class DataConf
{
    static get formInputs(): {[key: string]: SimpleFormControlData[]}
    {
        let optionsProtocolloIn: Array<SelectItem> = [
            {label: "IMAP"      , value: "IMAP"},
            {label: "IMAP IDLE" , value: "IMAP_IDLE"},
            {label: "IMAPS"     , value: "IMAPS"},
            {label: "IMAPS IDLE", value: "IMAPS_IDLE"},
            {label: "POP3"      , value: "POP3"}
        ];
        let optionsProtocolloOut: Array<SelectItem> = [
            {label: "SMTP"      , value: "SMTP"},
            {label: "SMTPS"     , value: "SMTPS"}
        ];

        return {           
            "sistemaProtocollazione": [
                { formControlName: "protocollazioneHashAlgorithm", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.Hash algorithm", required: true },
                { formControlName: "protocollazioneEndpoint", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.Endpoint", required: true },
                { formControlName: "protocollazioneAdministration", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.Administration", required: true },
                { formControlName: "protocollazioneDenominazione", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.denominazione", required: true },
                { formControlName: "protocollazioneAoo", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.AOO", required: true },
                { formControlName: "protocollazioneAooDenominazione", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.denominazioneAoo", required: true },
                { formControlName: "protocollazioneUser", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.User", required: true },
                { formControlName: "protocollazionePassword", type: "password", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.Password", required: true },
                { formControlName: "protocollazioneNumeroRegistrazione", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.numeroRegistrazione", required: true },
                { formControlName: "protocollazioneDataRegistrazione", type: "date", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.dataRegistrazione", required: true },
                { formControlName: "protocollazioneIndirizzoPostale", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.indirizzoPostale", required: true },
                { formControlName: "protocollazioneRegister", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.Register", required: true },
                { formControlName: "protocollazioneIndirizzoTelematico", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.indirizzoTelematico", required: true },
                { formControlName: "protocollazioneTipoIndirizzoTelematico", type: "text", label: "FUNZIONI_ADMIN.PROTOCOLLAZIONE_FIELDS.tipoIndirizzoTelematico", required: true },
                
            ],
            "sistemaPagamento": [
                { formControlName: "pagamentoCodEnte", type: "text", label: "FUNZIONI_ADMIN.MYPAY.codice_ipa", required: true },
                { formControlName: "pagamentoEndPoint", type: "text", label: "FUNZIONI_ADMIN.MYPAY.url", required: true },
                { formControlName: "pagamentoPassword", type: "password", label: "FUNZIONI_ADMIN.MYPAY.password", required: true },
                { formControlName: "pagamentoTipologia", type: "text", label: "FUNZIONI_ADMIN.MYPAY.tipologia", required: true },
                { formControlName: "pagamentoCommissione", type: "number", label: "FUNZIONI_ADMIN.MYPAY.commissione", required:true},
                { formControlName: "pagamentoRegexIud", type: "text", label: "FUNZIONI_ADMIN.MYPAY.regexIud", required: true },
                { formControlName: "pagamentoTipoRiscossione", type: "text", label: "FUNZIONI_ADMIN.MYPAY.tipo_riscoss", required: true },
                { formControlName: "bilancio", type: "textarea", label: "FUNZIONI_ADMIN.MYPAY.bilancio", required: false, tooltip: 'il valore deve essere scritto in formato JSON, Es: <br><strong>[<br>{<br>"codCapitolo": "E3062400_035",<br>"codUfficio": "145",<br>"accertamento": []<br>}<br>]</strong>'},
            ],
            "sistemaPec": [
                {
                    type: "category",
                    label: "Casella postale",
                    children: [
                        { formControlName: "pecIndirizzo", type: "text", label: "Indirizzo email", required: true, validators: [customEmailValidator] },
                        { formControlName: "pecNome", type: "text", label: "nome visualizzato", required: true },
                        { formControlName: "pecUsername", type: "text", label: "Username", required: true},
                        { formControlName: "pecPassword", type: "password", label: "Password", required: true },
                    ]
                },
                {
                    type: "category",
                    label: "Configurazione mail in ingresso",
                    children: [
                        { formControlName: "pecHostIn", type: "text", label: "Host", required: true },
                        { formControlName: "pecProtocolloIn", type: "checkbox", label: "Protocollo", required: false },
                        { formControlName: "pecPortaSslIn", type: "number", label: "Port", required: true },
                        { formControlName: "pecTipoProtocolloIn", type: "dropdown", label: "Protocollo", options: optionsProtocolloIn, required: true },
                        { formControlName: "pecSslIn", type: "checkbox", label: "Richiede Ssl", required: true },
                        { formControlName: "pecStartTlsIn", type: "checkbox", label: "Richiede start tsl", required: false },
                        { formControlName: "pecAutenticazioneIn", type: "checkbox", label: "Richiede autenticazione", required: false },
                    ]
                },
                {
                    type: "category",
                    label: "Configurazione mail in uscita",
                    children: [
                        { formControlName: "pecHostOut", type: "text", label: "Host", required: true },
                        { formControlName: "pecPortaSslOut", type: "number", label: "Porta ssl", required: true },
                        { formControlName: "pecPortaTlsOut", type: "number", label: "Porta tls", required: true },
                        { formControlName: "pecTipoProtocolloOut", type: "dropdown", label: "Protocollo", options: optionsProtocolloOut, required: true },
                        { formControlName: "pecSslOut", type: "checkbox", label: "Richiede ssl", required: false },
                        { formControlName: "pecTlsOut", type: "checkbox", label: "Richiede tls", required: false },
                        { formControlName: "pecStartTlsOut", type: "checkbox", label: "Richiede start tls", required: false },
                        { formControlName: "pecAutenticazioneOut", type: "checkbox", label: "Richiede autenticazione", required: false },
                    ]
                }
            ],
            "mailDefault": [
                {formControlName: "indirizziMailDefault", type: "textarea", label:"FUNZIONI_ADMIN.MAIL_DEFAULT_LABEL", required: true}
            ]
        }
    }
}