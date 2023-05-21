import { DropdownPipe } from './dropdown/dropdown.pipe';
import { ValueOfPipe } from './valueOf/value-of.pipe';
import { SafeHtmlPipe } from './safeHtml/safe-html.pipe';
import { FilterPipe } from './filter.pipe';
import { DestinatariPipe } from './destinatari/destinatari.pipe';
import { FileSizePipe } from './filesize/filesize.pipe';

export const pipes = 
[
    FilterPipe,
    SafeHtmlPipe,
    ValueOfPipe,
    DropdownPipe,
    DestinatariPipe,
    FileSizePipe
];

export { DropdownPipe } from './dropdown/dropdown.pipe';
export { FilterPipe } from './filter.pipe';
export { SafeHtmlPipe } from './safeHtml/safe-html.pipe';
export { ValueOfPipe } from './valueOf/value-of.pipe';
export { DestinatariPipe } from './destinatari/destinatari.pipe';
export { FileSizePipe } from './filesize/filesize.pipe';