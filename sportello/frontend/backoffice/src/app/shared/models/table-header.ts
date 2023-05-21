export class TableHeader {
    field?: string;
    header?: string;
    sortDirection?: boolean;
    width?: number;
}

export class SortHeader {
    field: string = '';
    direction: '-' | '' = '';
}