import { SelectItem } from 'primeng/primeng';

export interface Configurazione
{
    sistemaProtocollazione: boolean; //true manuale, false automatica
    protocollazioneEndpoint: string;
    protocollazioneAdministration: string;
    protocollazioneAoo: string;
    protocollazioneRegister: string;
    protocollazioneUser: string;
    protocollazionePassword: string;
    protocollazioneHashAlgorithm: string;
    protocollazioneIndirizzoPostale:String;
    protocollazioneIndirizzoTelematico:String;
    protocollazioneTipoIndirizzoTelematico:String;
    protocollazioneAooDenominazione:String;
    protocollazioneDenominazione:String;
    protocollazioneNumeroRegistrazione:String;
    protocollazioneDataRegistrazione:Date
    sistemaPagamento: boolean;
    pagamentoTipoRiscossione: string;
    pagamentoCodEnte: string;
    pagamentoTipologia: string;
    pagamentoEndPoint: string;
    pagamentoPassword: string;
    pagamentoCommissione: number;
    pagamentoRegexIud: string;
    sistemaPec: boolean; //true manuale, false automatica
    pecIndirizzo: string;
    pecNome: string;
    pecUsername: string;
    pecPassword: string;
    pecHostIn: string;
    pecHostOut: string;
    pecProtocolloIn: boolean;
    pecTipoProtocolloIn: string;
    pecTipoProtocolloOut: string;
    pecSslIn: boolean;
    pecSslOut: boolean;
    pecTlsOut: boolean;
    pecPortaSslIn: number;
    pecPortaSslOut: number;
    pecPortaTlsOut: number;
    pecStartTlsIn: boolean;
    pecStartTlsOut: boolean;
    pecAutenticazioneIn: boolean;
    pecAutenticazioneOut: boolean;
    pubblicazioneStatoPratica: boolean; //true manuale, false automatica, null default
    canCreateConferenza: boolean;
    indirizziMailDefault: string;
    bilancio: string;
}

export interface SimpleFormControlData
{
    formControlName?: string;
    type: "text"|"password"|"number"|"email"|"url"|"checkbox"|"dropdown"|"category"|"date"|"textarea";
    label: string;
    required?: boolean;
    children?: SimpleFormControlData[];
    validators?: any[];
    options?: SelectItem[];
    tooltip?: string;
}