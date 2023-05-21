import { StatoEnum } from 'src/app/shared/models/models';
export abstract class Paging
{
    page: number;
    limit: number;

    constructor(page?: number, limit?: number)
    {
        this.page = page ? page : 0;
        this.limit = limit ? limit : 0;
    }
}

/**
 * @description Classe per le ricerche che estende già di per sè la paginazione. 
 */
export abstract class  OrderBy extends Paging
{
    sortBy: string;
    sortType: "ASC"|"DESC"| null;

    constructor(column?: string, direction?: "ASC"|"DESC", page?: number, limit?: number)
    {
        super(page, limit);
        this.sortBy = column ? column : null;
        this.sortType = direction ? direction : null;
    }
}

export class AssegnazioniSearch extends OrderBy
{
    giaAssegnato: boolean;
    codice: string;

    constructor()
    {
        super('codicePraticaAppptr', 'ASC', 1, 5);
    }
}

/**
 * @description classe con funzione di model per le ricerche dei fascicoli.
 * @extends @class OrderBy
 */
export class FascicoloSearch extends OrderBy
{
    stato: StatoEnum;
    codiceFascicolo: string;
    oggetto: string;
    tipoProcedimento: string;
    comune: string;
    ente: string;
    funzionari: string[];
    funzionariSop: string[];
    onlyAssigned: boolean;

    constructor(codiceFascicolo?: string, stato?: StatoEnum, oggetto?: string,
                tipoProcedimento?: string, comune?: string, ente?: string, funzionari?: string[],
                funzionariSop?: string[], column?: string, direction?: "ASC"|"DESC",
                 page?: number, limit?: number)
    {
        super(column, direction, page, limit);
        this.codiceFascicolo = codiceFascicolo ? codiceFascicolo : null;
        this.oggetto = oggetto ? oggetto : null;
        this.stato = stato ? stato : null;
        this.tipoProcedimento = tipoProcedimento ? tipoProcedimento : null;
        this.comune = comune ? comune : null;
        this.ente = ente ? ente : null;
        this.funzionari = funzionari && funzionari.length > 0 ? funzionari : null;
        this.funzionariSop = funzionariSop && funzionariSop.length > 0 ? funzionariSop : null;
        this.onlyAssigned = false;
    }
}

export class PraticaSearch extends OrderBy
{
    public id: string;
    public codicePraticaAppptr: string;
    public enteDelegato: string;
    public tipoProcedimento: string;
    public stato: StatoEnum;
    public dataCreazione: Date;
    public oggetto: string;
    public dataModifica: Date;
    public inIstruttoria: true;
    public idSeduta: number;
    public editable: boolean;
    public escludiAccertamento: boolean;

    constructor()
    {
        super("codicePraticaAppptr", "DESC", 1, 5);
    }

}

/**
 * private Date dataSedutaDa;
	private Date dataSedutaA;
 */

export class SedutaCommissioneLocaleSearch extends OrderBy
{
    public dataSedutaDa : Date;
    public dataSedutaA  : Date;
    public codicePratica: string;
}