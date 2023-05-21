import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'orderBy'
})
export class OrderByPipe implements PipeTransform 
{
  transform(value: any[], args?: string): any[]
  {
    return value.sort((a1, a2) => a1[args] - a2[args]);
  }
}
