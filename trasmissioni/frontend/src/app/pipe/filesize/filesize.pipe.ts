import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'filesize'
})
export class FileSizePipe implements PipeTransform {
    transform(size: number, extension: string = 'MB',decimali:number=2) {
        if (size >= 1024 * 1024) {
            return (size / (1024 * 1024)).toFixed(decimali) + extension;
        } else if (size > 1024) {
            return (size / (1024)).toFixed(decimali) + 'KB';
        }
        else {
            return (size) + 'B';
        }
    }
}