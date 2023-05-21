import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'safeHtml'
})
export class SafeHtmlPipe implements PipeTransform 
{
  constructor(private sanitizer: DomSanitizer) { }

  transform(html: any): any
  {
    let value = html ? this.sanitizer.bypassSecurityTrustHtml(html) : null;
    return value ? value : "";
  }
}