export class AssegnamentoFascicolo {
    idFascicolo: number;
    idOrganizzazione: number;
    fase: string;
    usernameFunzionario: string;
    denominazioneFunzionario: string;
    numAssegnazioni: number;
    dataOperazione: Date;
}

export class StoricoAssegnamento {
    id: number;
    idFascicolo: number;
    idOrganizzazione: number;
    fase: string;
    usernameFunzionario: string;
    denominazioneFunzionario: string;
    dataOperazione: Date;
    tipoOperazione: string; /* ASSEGNAZIONE | DISASSEGNAZIONE */
}

export class ConfigurazioneAssegnamento {
    idOrganizzazione: number;
    fase: string;
    criterioAssegnamento: string; /* MANUALE | LOCALIZZAZIONE | TIPO_PROCEDIMENTO |DISATTIVATA*/
    valoriAssegnati: Array<ValoriAssegnamento>;
}

export class ValoriAssegnamento {
    idOrganizzazione: number;
    fase: string;
    idComuneTipoProcedimento: string;
    denominazioneComuneProcedimento: string;
    usernameFunzionario: string;
    denominazioneFunzionario: string;
    tipoAssegnamento: string;
}

export class TabelleAssegnamentoFascicoli {
    id: number;
    stato: string;
    codice: string;
    tipoProcedimento: string
    oggettoIntervento: string;
    comuni: string[];
    usernameFunzionario: string;
    denominazioneFunzionario: string;
    riassegnazioni: number;
    ultimaAssegnazione: Date;
    ultimaOperazione: Date;
    storico: StoricoAssegnamento[];
}