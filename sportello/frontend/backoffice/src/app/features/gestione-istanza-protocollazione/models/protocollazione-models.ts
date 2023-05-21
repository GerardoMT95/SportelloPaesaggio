export class Protocollo
{
    codiceFascicolo: string;
    numeroProtocollo: string;
    dataProtocollo: Date;

    constructor(codiceFascicolo?: string, numeroProtocollo?: string, dataProtocollo?: Date)
    {
        this.codiceFascicolo = codiceFascicolo ? codiceFascicolo : null;
        this.numeroProtocollo = numeroProtocollo ? numeroProtocollo : null;
        this.dataProtocollo = dataProtocollo ? dataProtocollo : null;
    }
}