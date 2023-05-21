export function swipe(array: any[], leftIndex: number, rightIndex: number): void
{
    let tmp: any = array[leftIndex];
    array[leftIndex] = array[rightIndex];
    array[rightIndex] = tmp;
}