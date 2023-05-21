export class TableHeader {
    field?: string;
    header?: string;
    sortDirection?: boolean;
    width?: number;
    sortableField?:boolean;    
}

export class SortHeader {
    field: string = '';
    direction: '-' | '' = '';
}