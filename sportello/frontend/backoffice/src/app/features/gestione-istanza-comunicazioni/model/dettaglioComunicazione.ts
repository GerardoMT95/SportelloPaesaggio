export class DettaglioCom
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
    /* allegati: AllegatoCustomDTO[]; */
    //idCms:string;
    ricevutaAccetazione: Ricevuta;
    destinatari: Destinatari[];
}

export class Allegato
{
    id:string;
    dimensione:number;
    idCmis:string;
    nomeOriginale:string;
    nrProtocollo:string;
}

export class Ricevuta
{
    data:string;
    tipoRicevuta: string;
    errore:string;
    descrizioneErrore:string;
    idCmsEml:string;
    idCmsDatiCert:string;
    idCmsSmime:string;
}

export class Destinatari
{
    tipoDestinatario:string;
    denominazione:string;
    nominativo:string;
    idDestinatario:string;
    indirizzo:string;
    ricevute:Ricevuta[];
}
