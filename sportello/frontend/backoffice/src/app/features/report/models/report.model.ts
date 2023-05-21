
export interface ReportSelectItemEstensione {
    labelValue: ReportSelectItem;
    formati: HashMap<String[]>;
}

export interface ReportSelectItem {
    label: string;
    value: string;
}

export interface ReportList {
    id: string;
    fileName: string;
    hash: string;
    stato: string;
    dataCreazione: Date;
}

export interface BaseSearchRequest {
    page: number;
    limit: number;
    sortBy: string;
    sortType: 'asc' | 'desc';
}

export interface ReportSearch extends BaseSearchRequest {
    dataFrom: Date;
    dataTo: Date;
}

export interface ReportInsert {
    tipo: string;
    formato: string;
    dataFrom: Date;
    dataTo: Date;
    tipoProcedimento: string[];
}
