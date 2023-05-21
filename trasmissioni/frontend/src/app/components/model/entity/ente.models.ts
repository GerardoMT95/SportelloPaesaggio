export enum TipoEnte
{
    regione="R",
	comune="CO",
	provincia="P",
	ente="E",
	consorzio="CN",
	sito_sic="SS",
	sito_zps="SZ",
	soprintendenza="SI",
	ufficio_regionale="UR",
	altro="ZZ"
}

export class EnteDTO
{
    id: number;
    nome: string;
    descrizione: string;
    codice: string;
    pec: string;
    tipo: TipoEnte;
}