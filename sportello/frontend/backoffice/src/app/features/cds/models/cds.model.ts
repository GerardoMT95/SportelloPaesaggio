export interface CdsSettings{
    id?:string;
    tipo?:string;
    tipoLabel?:string;
	attivita?:string;
	attivitaLabel?:string;
	azione?:string;
	azioneLabel?:string;
	termineRichiestaIntegrazione?:Date;
	terminePareri?:Date;
	primaSessione?:Date;
	dataTermine?:Date;
	comunePertinenza?:string;
	comunePertinenzaLabel?:string;
	provinciaPertinenza?:string;
	provinciaPertinenzaLabel?:string;
	indirizzoPertinenza?:string;
	modalitaIncontro?:string;
	modalitaIncontroLabel?:string;
	link?:string;
	comune?:string;
	comuneLabel?:string;
	provincia?:string;
	provinciaLabel?:string;
	cap?:string;
	indirizzo?:string;
	orario?:string;
	completed?:boolean;
	idCds?:number;
	usernameCreatore?:string;
	usernameCreatoreNominativo?:string;
	usernameResponsabile?:string;
	nominativoResponsabile?:string;
	partecipanti?:CdsSelectItem[];
}

export interface CdsSelectItem {
    label: string;
    value: string;
	description: string;
}

export interface Cds {
    id: number;
    riferimentoIstanza: string;
    stato: string;
}
export interface CdsDocument {
	id?:number;
	nome?:string;
	categoria?:string;
	tipo?:string;
	protocollo?:string;
	dataProtocollo?:string;
	dataCreazione?:Date;
	idTipoDocumento?:string;
	tipoDocumento?:string;
	idConferenza?:string;
	identificativoConferenza?:string;
	statoConferenza?:string;
	idTipo?:string;
	idPratica?:string;
	idDocumentoCds?:string;
}

export interface CdsListItem{
	id?:string;
	idCds?:number;
	tipo?:string;
	attivita?:string;
	azione?:string;
	completed?:boolean;
}