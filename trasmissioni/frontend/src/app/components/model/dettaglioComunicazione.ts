import { AllegatoCustomDTO } from './entity/allegato.models';

export type DettaglioCom = 
{
    codiceComunicazione: string;
    data:string;
    numeroProtocollo: string;
    mittente:string;
    mailMittente:string;
    oggetto:string;
    tipologia:string;
    pec:boolean;
    testo: string;
    //allegati:Allegato[];
    allegati: AllegatoCustomDTO[];
    //idCms:string;
    ricevutaAccettazione: Ricevuta;
    destinatari: Destinatari[];

    idCms?: any;
}

export type Allegato=
{
    id:string;
    dimensione:number;
    idCmis:string;
    nomeOriginale:string;
    nrProtocollo:string;
}

export type Ricevuta=
{
    data:string;
    tipoRicevuta: string;
    errore:string;
    descrizioneErrore:string;
    idCmsEml:string;
    idCmsDatiCert:string;
    idCmsSmime:string;
    id?:number;
    idCorrispondenza?:number;
}

export type Destinatari=
{
    tipoDestinatario:string;
    denominazione:string;
    nominativo:string;
    idDestinatario:string;
    indirizzo:string;
    ricevute:Ricevuta[];
}
