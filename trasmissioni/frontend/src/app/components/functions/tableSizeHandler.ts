export type SCREEN_SIZE = 'large'|'medium'|'small'|'very_small';

export function fromWidthToSize(width: number): SCREEN_SIZE
{
    if(width >= 1000)
        return 'large';
    if(width >= 600)
        return 'medium';
    if(width >= 360)
        return 'small';
    else
        return 'very_small';
}

export function fromSizeToPaginatorElements(size: SCREEN_SIZE): number
{
    switch(size)
    {
        case 'very_small':
            return 2;
        case 'small':
            return 3;
        case 'medium':
            return 6;
        case 'large':
            return 10;
    }
}