export interface DestinatariComunicazione{
    denominazione:string;
    mailAddress:string;
    to:boolean;//false se è da utilizzare per  cc
};

export class Recipient {
    email?: string;
    accettazione?: boolean;
    consegna?: boolean;
}