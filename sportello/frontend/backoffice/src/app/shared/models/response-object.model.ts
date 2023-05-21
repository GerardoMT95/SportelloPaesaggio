export class PageObj<T> {
    list: T[];
    count: number;
    pageNumber: number;
    pageSize: number;
}

export class SortObj {
    unsorted: boolean; 
    sorted: boolean; 
    empty: boolean;
}

